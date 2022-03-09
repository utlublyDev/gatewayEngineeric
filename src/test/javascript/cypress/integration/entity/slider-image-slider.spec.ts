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

describe('SliderImageSlider e2e test', () => {
  const sliderImageSliderPageUrl = '/slider-image-slider';
  const sliderImageSliderPageUrlPattern = new RegExp('/slider-image-slider(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sliderImageSliderSample = {};

  let sliderImageSlider: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/slider-image-sliders+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/slider-image-sliders').as('postEntityRequest');
    cy.intercept('DELETE', '/api/slider-image-sliders/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sliderImageSlider) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/slider-image-sliders/${sliderImageSlider.id}`,
      }).then(() => {
        sliderImageSlider = undefined;
      });
    }
  });

  it('SliderImageSliders menu should load SliderImageSliders page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('slider-image-slider');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SliderImageSlider').should('exist');
    cy.url().should('match', sliderImageSliderPageUrlPattern);
  });

  describe('SliderImageSlider page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sliderImageSliderPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SliderImageSlider page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/slider-image-slider/new$'));
        cy.getEntityCreateUpdateHeading('SliderImageSlider');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sliderImageSliderPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/slider-image-sliders',
          body: sliderImageSliderSample,
        }).then(({ body }) => {
          sliderImageSlider = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/slider-image-sliders+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/slider-image-sliders?page=0&size=20>; rel="last",<http://localhost/api/slider-image-sliders?page=0&size=20>; rel="first"',
              },
              body: [sliderImageSlider],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(sliderImageSliderPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SliderImageSlider page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sliderImageSlider');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sliderImageSliderPageUrlPattern);
      });

      it('edit button click should load edit SliderImageSlider page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SliderImageSlider');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sliderImageSliderPageUrlPattern);
      });

      it('last delete button click should delete instance of SliderImageSlider', () => {
        cy.intercept('GET', '/api/slider-image-sliders/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('sliderImageSlider').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', sliderImageSliderPageUrlPattern);

        sliderImageSlider = undefined;
      });
    });
  });

  describe('new SliderImageSlider page', () => {
    beforeEach(() => {
      cy.visit(`${sliderImageSliderPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SliderImageSlider');
    });

    it('should create an instance of SliderImageSlider', () => {
      cy.get(`[data-cy="storeOwnerId"]`)
        .type('out-of-the-box transmitting transparent')
        .should('have.value', 'out-of-the-box transmitting transparent');

      cy.get(`[data-cy="webKey"]`).type('Plastic deposit').should('have.value', 'Plastic deposit');

      cy.get(`[data-cy="imageUrl"]`).type('Future payment').should('have.value', 'Future payment');

      cy.get(`[data-cy="alt"]`).type('COM').should('have.value', 'COM');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        sliderImageSlider = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', sliderImageSliderPageUrlPattern);
    });
  });
});
