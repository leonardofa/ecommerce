# G1:
## API:
### DB: H2
### Arq style: Hexagonal + Rest API w/ simple controllers + 
### Basic Auth:
- (admin/admin)
- (user/user)
### Documentation: Swagger (OpenApi Doc) & .md files
___
#### Product:
__create product__
- [x] core
  - name
  - price
  - initial stock
- [X] core red/green/refactor
  - content validation
  - unique validation (name)
  - sku generation
  - persistence save
- [X] infrastructure
- [X] documentation
- [X] security: only for admin

* edit product by sku
- [X] core
- name
- price
- [X] core red/green/refactor
    - content validation
    - unique validation
    - persistence save
- [X] infrastructure
- [X] documentation
- [X] security: only for admin

* enable product by sku
- [X] core
- [X] core red/green/refactor
    - enable if disable
    - persistence save
- [X] infrastructure
- [X] documentation
- [X] security: only for admin

* disable product
- [X] core
- [X] core red/green/refactor
    - enable if disable
    - persistence save
- [X] infrastructure
- [X] documentation
- [X] security: only for admin

* delete unused product
- [X] core
- [X] core red/green/refactor
    - content validation
    - unique validation
    - persistence save
- [X] infrastructure
- [X] documentation
- [X] security: only for admin
___