package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Entity.MenClothList;
import in.freakydevs.dhobiexpress.Entity.OtherCLothList;
import in.freakydevs.dhobiexpress.Entity.WomenClothList;
import in.freakydevs.dhobiexpress.api.RestServices.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cloth")
public class Clothes {

	@Autowired
	private ClothService clothService;

	@RequestMapping("/men")
	public List<MenClothList> getMenClothList() {
		return clothService.getMenClotList();
	}

	@RequestMapping("/women")
	public List<WomenClothList> getWomenenClothList() {
		return clothService.getWomenClothList();
	}

	@RequestMapping("/other")
	public List<OtherCLothList> getOtherClothList() {
		return clothService.getOtherClothlist();
	}

}
