package mx.com.acerozin.security

import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional
import mx.com.acerozin.core.company.Company
import mx.com.acerozin.pogo.security.RoleWrapper

@Transactional
@GrailsCompileStatic
class RoleService {

    Collection<RoleWrapper> getAllRolesByCompanyId(Long companyId) {
        log.info "Obtaining roles for companyId: " + companyId
        List<RoleWrapper> result = new ArrayList<RoleWrapper>()

        Company.findAll() {
            id == companyId
        }.each {
            Company company = (Company) it
            company.roles.each {
                result << (it as RoleWrapper)
            }
        }
        log.info "Obtained roles for companyId: " + result.dump()
        return result

    }

}
