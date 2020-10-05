import http from '../http-common'

class PersonDataService {
    create(person) {
        return http.post('/persons', person)
    }

    update(id, person) {
        return http.put(`/persons/${id}`, person)
    }

    delete(id) {
        return http.delete(`/persons/${id}`)
    }

    findById(id) {
        return http.get(`/persons/${id}`)
    }

    findAll(name, pageable) {
        if (name === null)
            return http.get(`/persons?${pageable}`)
        else
            return http.get(`/persons?name=${name}&${pageable}`)
    }
}

export default new PersonDataService()