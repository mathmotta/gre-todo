import hc from '../http-common'

class BuildingDataService {
    create(building){
        hc.post('/buildings', building)
    }

    update(building){
        hc.put('/buildings', building)
    }

    delete(id){
        hc.delete(`/buildings/${id}`)
    }

    findById(id){
        hc.get(`/buildings/${id}`)
    }

    findAll(name,  pageable) {
        if(name===null)
            return hc.get(`/buildings?${pageable}`)
        else
            return hc.get(`/buildings?name=${name}&${pageable}`)
    }
}

export default new BuildingDataService()