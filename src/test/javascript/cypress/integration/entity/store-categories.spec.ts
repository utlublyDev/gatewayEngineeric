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

describe('StoreCategories e2e test', () => {
  const storeCategoriesPageUrl = '/store-categories';
  const storeCategoriesPageUrlPattern = new RegExp('/store-categories(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const storeCategoriesSample = {
    userStoreOwnerId: 'payment copy',
    categoryName: 'Cloned invoice Computers',
    description: 'Yen',
    categoryNameInArabic: 'AGP Profit-focused',
  };

  let storeCategories: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/store-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/store-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/store-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (storeCategories) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/store-categories/${storeCategories.id}`,
      }).then(() => {
        storeCategories = undefined;
      });
    }
  });

  it('StoreCategories menu should load StoreCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('store-categories');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StoreCategories').should('exist');
    cy.url().should('match', storeCategoriesPageUrlPattern);
  });

  describe('StoreCategories page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(storeCategoriesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StoreCategories page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/store-categories/new$'));
        cy.getEntityCreateUpdateHeading('StoreCategories');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeCategoriesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/store-categories',
          body: storeCategoriesSample,
        }).then(({ body }) => {
          storeCategories = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/store-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [storeCategories],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(storeCategoriesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StoreCategories page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('storeCategories');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeCategoriesPageUrlPattern);
      });

      it('edit button click should load edit StoreCategories page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StoreCategories');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeCategoriesPageUrlPattern);
      });

      it('last delete button click should delete instance of StoreCategories', () => {
        cy.intercept('GET', '/api/store-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('storeCategories').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeCategoriesPageUrlPattern);

        storeCategories = undefined;
      });
    });
  });

  describe('new StoreCategories page', () => {
    beforeEach(() => {
      cy.visit(`${storeCategoriesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StoreCategories');
    });

    it('should create an instance of StoreCategories', () => {
      cy.get(`[data-cy="userStoreOwnerId"]`).type('Coordinator').should('have.value', 'Coordinator');

      cy.get(`[data-cy="categoryName"]`).type('Idaho Functionality connecting').should('have.value', 'Idaho Functionality connecting');

      cy.get(`[data-cy="description"]`).type('Tuna').should('have.value', 'Tuna');

      cy.get(`[data-cy="categoryNameInArabic"]`).type('payment overriding').should('have.value', 'payment overriding');

      cy.get(`[data-cy="descriptionInArabic"]`).type('Tuna').should('have.value', 'Tuna');

      cy.get(`[data-cy="userStoreOwnerIdImageUrl"]`).type('Paradigm').should('have.value', 'Paradigm');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        storeCategories = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', storeCategoriesPageUrlPattern);
    });
  });
});
