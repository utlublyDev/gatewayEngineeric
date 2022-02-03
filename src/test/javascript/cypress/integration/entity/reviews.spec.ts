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

describe('Reviews e2e test', () => {
  const reviewsPageUrl = '/reviews';
  const reviewsPageUrlPattern = new RegExp('/reviews(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const reviewsSample = {};

  let reviews: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/product/api/reviews+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/product/api/reviews').as('postEntityRequest');
    cy.intercept('DELETE', '/services/product/api/reviews/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (reviews) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/product/api/reviews/${reviews.id}`,
      }).then(() => {
        reviews = undefined;
      });
    }
  });

  it('Reviews menu should load Reviews page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('reviews');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Reviews').should('exist');
    cy.url().should('match', reviewsPageUrlPattern);
  });

  describe('Reviews page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(reviewsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Reviews page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/reviews/new$'));
        cy.getEntityCreateUpdateHeading('Reviews');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/product/api/reviews',
          body: reviewsSample,
        }).then(({ body }) => {
          reviews = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/product/api/reviews+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/product/api/reviews?page=0&size=20>; rel="last",<http://localhost/services/product/api/reviews?page=0&size=20>; rel="first"',
              },
              body: [reviews],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(reviewsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Reviews page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('reviews');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewsPageUrlPattern);
      });

      it('edit button click should load edit Reviews page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Reviews');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewsPageUrlPattern);
      });

      it('last delete button click should delete instance of Reviews', () => {
        cy.intercept('GET', '/services/product/api/reviews/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('reviews').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewsPageUrlPattern);

        reviews = undefined;
      });
    });
  });

  describe('new Reviews page', () => {
    beforeEach(() => {
      cy.visit(`${reviewsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Reviews');
    });

    it('should create an instance of Reviews', () => {
      cy.get(`[data-cy="customerId"]`).type('lime Loan invoice').should('have.value', 'lime Loan invoice');

      cy.get(`[data-cy="orderId"]`).type('Representative').should('have.value', 'Representative');

      cy.get(`[data-cy="review"]`).type('Fresh').should('have.value', 'Fresh');

      cy.get(`[data-cy="rating"]`).type('95534').should('have.value', '95534');

      cy.get(`[data-cy="createdAt"]`).type('2022-02-03T02:06').should('have.value', '2022-02-03T02:06');

      cy.get(`[data-cy="prodcutsId"]`).type('expedite reboot').should('have.value', 'expedite reboot');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        reviews = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', reviewsPageUrlPattern);
    });
  });
});
