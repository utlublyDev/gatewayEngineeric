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

describe('StoreAdvBanner e2e test', () => {
  const storeAdvBannerPageUrl = '/store-adv-banner';
  const storeAdvBannerPageUrlPattern = new RegExp('/store-adv-banner(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const storeAdvBannerSample = {};

  let storeAdvBanner: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/store-adv-banners+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/store-adv-banners').as('postEntityRequest');
    cy.intercept('DELETE', '/api/store-adv-banners/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (storeAdvBanner) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/store-adv-banners/${storeAdvBanner.id}`,
      }).then(() => {
        storeAdvBanner = undefined;
      });
    }
  });

  it('StoreAdvBanners menu should load StoreAdvBanners page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('store-adv-banner');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StoreAdvBanner').should('exist');
    cy.url().should('match', storeAdvBannerPageUrlPattern);
  });

  describe('StoreAdvBanner page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(storeAdvBannerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StoreAdvBanner page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/store-adv-banner/new$'));
        cy.getEntityCreateUpdateHeading('StoreAdvBanner');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeAdvBannerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/store-adv-banners',
          body: storeAdvBannerSample,
        }).then(({ body }) => {
          storeAdvBanner = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/store-adv-banners+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [storeAdvBanner],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(storeAdvBannerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StoreAdvBanner page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('storeAdvBanner');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeAdvBannerPageUrlPattern);
      });

      it('edit button click should load edit StoreAdvBanner page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StoreAdvBanner');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeAdvBannerPageUrlPattern);
      });

      it('last delete button click should delete instance of StoreAdvBanner', () => {
        cy.intercept('GET', '/api/store-adv-banners/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('storeAdvBanner').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeAdvBannerPageUrlPattern);

        storeAdvBanner = undefined;
      });
    });
  });

  describe('new StoreAdvBanner page', () => {
    beforeEach(() => {
      cy.visit(`${storeAdvBannerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StoreAdvBanner');
    });

    it('should create an instance of StoreAdvBanner', () => {
      cy.get(`[data-cy="storeOwnerId"]`).type('enhance knowledge Keyboard').should('have.value', 'enhance knowledge Keyboard');

      cy.get(`[data-cy="webKey"]`).type('Sports').should('have.value', 'Sports');

      cy.get(`[data-cy="imageUrl"]`).type('Garden').should('have.value', 'Garden');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        storeAdvBanner = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', storeAdvBannerPageUrlPattern);
    });
  });
});
