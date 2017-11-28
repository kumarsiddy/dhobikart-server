package in.freakydevs.dhobiexpress.api.RestServices;

import in.freakydevs.dhobiexpress.Entity.MenClothList;
import in.freakydevs.dhobiexpress.Entity.OtherCLothList;
import in.freakydevs.dhobiexpress.Entity.WomenClothList;
import in.freakydevs.dhobiexpress.api.RestRepository.MenClothListRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.OtherClothListRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.WomenClothListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothService {

	@Autowired
	private MenClothListRepo menClothListRepo;

	@Autowired
	private WomenClothListRepo womenClothListRepo;

	@Autowired
	private OtherClothListRepo otherClothListRepo;

	public List<MenClothList> getMenClotList() {
		return (List<MenClothList>) menClothListRepo.findAll();
	}

	public List<WomenClothList> getWomenClothList() {
		return (List<WomenClothList>) womenClothListRepo.findAll();
	}

	public List<OtherCLothList> getOtherClothlist() {
		return (List<OtherCLothList>) otherClothListRepo.findAll();
	}

}
