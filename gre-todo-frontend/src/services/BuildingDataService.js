import hc from '../http-common'

class BuildingDataService {
    findById(id) {
        return hc.get(`/buildings/${id}`)
    }
    create(building) {
        return hc.post('/buildings', building)
    }

    update(id, building) {
        return hc.put(`/buildings/${id}`, building)
    }

    delete(id) {
        return hc.delete(`/buildings/${id}`)
    }

    findAll(name, pageable) {
        if (name === null)
            return hc.get(`/buildings?${pageable}`)
        else
            return hc.get(`/buildings?name=${name}&${pageable}`)
    }
}

export default new BuildingDataService()