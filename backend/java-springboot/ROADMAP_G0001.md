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
- [ ] core red/green/refactor
  - content validation
  - unique validation (name)
  - sku generation
  - persistence save
- [ ] infrastructure
- [ ] documentation
- [ ] security: only for admin

* edit product by sku
- [ ] core
- name
- price
- [ ] core red/green/refactor
    - content validation
    - unique validation
    - persistence save
- [ ] infrastructure
- [ ] documentation
- [ ] security: only for admin

* enable product by sku
- [ ] core
- [ ] core red/green/refactor
    - enable if disable
    - persistence save
- [ ] infrastructure
- [ ] documentation
- [ ] security: only for admin

* disable product
- [ ] core
- [ ] core red/green/refactor
    - enable if disable
    - persistence save
- [ ] infrastructure
- [ ] documentation
- [ ] security: only for admin

* delete unused product
- [ ] core
- [ ] core red/green/refactor
    - content validation
    - unique validation
    - persistence save
- [ ] infrastructure
- [ ] documentation
- [ ] security: only for admin
___