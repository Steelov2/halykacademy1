package Service;

import entity.Organization;

import java.time.LocalDate;
import java.time.Month;
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

        this.addOrganization(new Organization(27408,"Yakudza","Empire of Japan", LocalDate.of(1989, Month.SEPTEMBER,2)));
        this.addOrganization(new Organization(27218,"Cosa Nostra","Italian Republic", LocalDate.of(1947, Month.NOVEMBER,28)));
        this.addOrganization(new Organization(25893,"Stidda","Italian Republic", LocalDate.of(1957, Month.OCTOBER,22)));
        this.addOrganization(new Organization(27408,"Tijuana Cartel","Mexico", LocalDate.of(1963, Month.AUGUST,24)));
    }

    public boolean delete(long id){
        if(this.organizations.containsValue(id)) {
            this.organizations.remove(id);
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
        catch (Exception e) {throw e;}
        return json;
    }

    private boolean addOrganization(Organization organization) {
        Integer id = key.incrementAndGet();
        organization.setId(id);
        this.organizations.put(id, organization);
        return true;
    }
}
