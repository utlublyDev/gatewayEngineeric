import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('TopBannerInformation e2e test', () => {
  const topBannerInformationPageUrl = '/top-banner-information';
  const topBannerInformationPageUrlPattern = new RegExp('/top-banner-information(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const topBannerInformationSample = {};

  let topBannerInformation: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/top-banner-informations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/top-banner-informations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/top-banner-informations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (topBannerInformation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/top-banner-informations/${topBannerInformation.id}`,
      }).then(() => {
        topBannerInformation = undefined;
      });
    }
  });

  it('TopBannerInformations menu should load TopBannerInformations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('top-banner-information');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TopBannerInformation').should('exist');
    cy.url().should('match', topBannerInformationPageUrlPattern);
  });

  describe('TopBannerInformation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(topBannerInformationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TopBannerInformation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/top-banner-information/new$'));
        cy.getEntityCreateUpdateHeading('TopBannerInformation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', topBannerInformationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/top-banner-informations',
          body: topBannerInformationSample,
        }).then(({ body }) => {
          topBannerInformation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/top-banner-informations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/top-banner-informations?page=0&size=20>; rel="last",<http://localhost/api/top-banner-informations?page=0&size=20>; rel="first"',
              },
              body: [topBannerInformation],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(topBannerInformationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TopBannerInformation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('topBannerInformation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', topBannerInformationPageUrlPattern);
      });

      it('edit button click should load edit TopBannerInformation page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TopBannerInformation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', topBannerInformationPageUrlPattern);
      });

      it('last delete button click should delete instance of TopBannerInformation', () => {
        cy.intercept('GET', '/api/top-banner-informations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('topBannerInformation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', topBannerInformationPageUrlPattern);

        topBannerInformation = undefined;
      });
    });
  });

  describe('new TopBannerInformation page', () => {
    beforeEach(() => {
      cy.visit(`${topBannerInformationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TopBannerInformation');
    });

    it('should create an instance of TopBannerInformation', () => {
      cy.get(`[data-cy="enableBanner"]`).should('not.be.checked');
      cy.get(`[data-cy="enableBanner"]`).click().should('be.checked');

      cy.get(`[data-cy="bannerText"]`).type('calculate').should('have.value', 'calculate');

      cy.get(`[data-cy="startBanner"]`).type('2022-02-19T15:40').should('have.value', '2022-02-19T15:40');

      cy.get(`[data-cy="endBanner"]`).type('2022-02-20T14:39').should('have.value', '2022-02-20T14:39');

      cy.get(`[data-cy="webKey"]`).type('Miquelon Intranet').should('have.value', 'Miquelon Intranet');

      cy.get(`[data-cy="storeOwnerId"]`).type('Money methodologies Montenegro').should('have.value', 'Money methodologies Montenegro');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        topBannerInformation = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', topBannerInformationPageUrlPattern);
    });
  });
});
