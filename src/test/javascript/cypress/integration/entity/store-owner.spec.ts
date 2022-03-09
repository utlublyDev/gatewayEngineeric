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

describe('StoreOwner e2e test', () => {
  const storeOwnerPageUrl = '/store-owner';
  const storeOwnerPageUrlPattern = new RegExp('/store-owner(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const storeOwnerSample = {
    userStoreOwnerId: 'COM panel',
    storeName: 'Engineer',
    storeNameInArabic: 'digital Configuration Indiana',
    address: 'auxiliary empower Advanced',
    addressInArabic: 'Canadian calculate',
    longitude: 79978,
    latitude: 71649,
    isBusy: true,
    city: 'Lake Laverna',
    cityInArabic: 'Account',
    hasDelivery: false,
    availableDateTime: '2022-02-03T04:17:11.285Z',
  };

  let storeOwner: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/store-owners+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/store-owners').as('postEntityRequest');
    cy.intercept('DELETE', '/api/store-owners/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (storeOwner) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/store-owners/${storeOwner.id}`,
      }).then(() => {
        storeOwner = undefined;
      });
    }
  });

  it('StoreOwners menu should load StoreOwners page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('store-owner');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StoreOwner').should('exist');
    cy.url().should('match', storeOwnerPageUrlPattern);
  });

  describe('StoreOwner page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(storeOwnerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StoreOwner page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/store-owner/new$'));
        cy.getEntityCreateUpdateHeading('StoreOwner');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeOwnerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/store-owners',
          body: storeOwnerSample,
        }).then(({ body }) => {
          storeOwner = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/store-owners+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [storeOwner],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(storeOwnerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StoreOwner page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('storeOwner');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeOwnerPageUrlPattern);
      });

      it('edit button click should load edit StoreOwner page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StoreOwner');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeOwnerPageUrlPattern);
      });

      it('last delete button click should delete instance of StoreOwner', () => {
        cy.intercept('GET', '/api/store-owners/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('storeOwner').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', storeOwnerPageUrlPattern);

        storeOwner = undefined;
      });
    });
  });

  describe('new StoreOwner page', () => {
    beforeEach(() => {
      cy.visit(`${storeOwnerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StoreOwner');
    });

    it('should create an instance of StoreOwner', () => {
      cy.get(`[data-cy="userStoreOwnerId"]`).type('Shirt Arizona Chicken').should('have.value', 'Shirt Arizona Chicken');

      cy.get(`[data-cy="storeName"]`).type('Intelligent Extension').should('have.value', 'Intelligent Extension');

      cy.get(`[data-cy="storeNameInArabic"]`).type('indigo benchmark').should('have.value', 'indigo benchmark');

      cy.get(`[data-cy="address"]`).type('Zealand').should('have.value', 'Zealand');

      cy.get(`[data-cy="addressInArabic"]`).type('Markets Money').should('have.value', 'Markets Money');

      cy.get(`[data-cy="longitude"]`).type('15813').should('have.value', '15813');

      cy.get(`[data-cy="latitude"]`).type('10627').should('have.value', '10627');

      cy.get(`[data-cy="isBusy"]`).should('not.be.checked');
      cy.get(`[data-cy="isBusy"]`).click().should('be.checked');

      cy.get(`[data-cy="city"]`).type('Ryanview').should('have.value', 'Ryanview');

      cy.get(`[data-cy="cityInArabic"]`).type('Mall').should('have.value', 'Mall');

      cy.get(`[data-cy="description"]`).type('1080p Handcrafted').should('have.value', '1080p Handcrafted');

      cy.get(`[data-cy="descriptionInArabic"]`).type('Krona synthesizing Practical').should('have.value', 'Krona synthesizing Practical');

      cy.get(`[data-cy="storeContactNumber"]`).type('Total Sports').should('have.value', 'Total Sports');

      cy.get(`[data-cy="createdDate"]`).type('2022-02-03T14:36').should('have.value', '2022-02-03T14:36');

      cy.get(`[data-cy="storeLogoUrl"]`).type('iterate Fantastic').should('have.value', 'iterate Fantastic');

      cy.get(`[data-cy="isActive"]`).should('not.be.checked');
      cy.get(`[data-cy="isActive"]`).click().should('be.checked');

      cy.get(`[data-cy="hasDelivery"]`).should('not.be.checked');
      cy.get(`[data-cy="hasDelivery"]`).click().should('be.checked');

      cy.get(`[data-cy="hasFreeDelivery"]`).should('not.be.checked');
      cy.get(`[data-cy="hasFreeDelivery"]`).click().should('be.checked');

      cy.get(`[data-cy="availableDateTime"]`).type('2022-02-03T09:45').should('have.value', '2022-02-03T09:45');

      cy.get(`[data-cy="shopOpeiningTime"]`).type('2022-02-03T12:10').should('have.value', '2022-02-03T12:10');

      cy.get(`[data-cy="shopClosingTime"]`).type('2022-02-02T23:37').should('have.value', '2022-02-02T23:37');

      cy.get(`[data-cy="currency"]`).type('payment Squares').should('have.value', 'payment Squares');

      cy.get(`[data-cy="deliveryCost"]`).type('5101').should('have.value', '5101');

      cy.get(`[data-cy="webKey"]`)
        .type('interface Internal Reverse-engineered')
        .should('have.value', 'interface Internal Reverse-engineered');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        storeOwner = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', storeOwnerPageUrlPattern);
    });
  });
});
