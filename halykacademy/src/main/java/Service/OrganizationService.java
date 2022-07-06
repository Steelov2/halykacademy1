package Service;

import entity.Organization;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class OrganizationService {
    private ConcurrentMap<Integer, Organization> organizations;
    private AtomicInteger key;

    public OrganizationService() {
        this.organizations = new ConcurrentHashMap<>();
        key = new AtomicInteger();

        this.addOrganization(new Organization(1, "Google", "USA", LocalDate.of(2018,01,01)));
        this.addOrganization(new Organization(2, "Yahoo", "USA", LocalDate.of(2019,01,01)));
    }

    public boolean delete(long id){
        if(this.organizations.containsKey(id)) {
//            this.organizations.remove(
        }
        return false;
    }

    public String findAllOrganizations() {
        List<Organization> list = new ArrayList<>(this.organizations.values());
        return this.toJson(list);
    }

    public boolean createOrganization(String jsonPayload) {
        if (jsonPayload == null) return false;

        Gson gson = new Gson();
        try {
            Organization organization = (Organization) gson.fromJson(jsonPayload, Organization.class);
            if (organization != null) {
                return this.addOrganization(organization);
            }
        }
        catch (Exception e) {}
        return false;
    }

    private String toJson(Object list) {
        if (list == null) return null;
        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(list);
        }
        catch (Exception e) {}
        return json;
    }

    private boolean addOrganization(Organization organization) {
        Integer id = key.incrementAndGet();
        organization.setId(id);
        this.organizations.put(id, organization);
        return true;
    }
}
