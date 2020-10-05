import hc from '../http-common'

class ProjectDataService {
    create(project){
        return hc.post('/projects', project)
    }

    update(id, project){
        return hc.put(`/projects/${id}`, project)
    }

    delete(id){
        return hc.delete(`/projects/${id}`)
    }

    findById(id){
        return hc.get(`/projects/${id}`)
    }

    findAll(pageable) {
        return hc.get(`/projects?${pageable}`)
    }
    findByFilter(pageable, filter) {
        return hc.post(`/projects/filter?${pageable}`, filter)
    }
}
export default new ProjectDataService()