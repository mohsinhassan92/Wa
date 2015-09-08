package nl.vogelbescherming.wadvogels.control;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.vogelbescherming.wadvogels.model.Bird;


public class Filter {
	public static enum Filters{
		Silhouette,
		Color,
		Size,
		Beak
	}
	
	private Bird myBird;
	
	public Filter(Bird bird){
		this.myBird = bird;
	}
	public boolean isFilterEnable(){
		
		if (myBird.getSilhouette() != null)
			if (myBird.getSilhouette().size() != 0)
				return true;
		if (myBird.getBeak() != null)
			if(myBird.getBeak().size() != 0)
				return true;
		if (myBird.getColors() != null)
            return true;
		if (myBird.getSizes() != null)
			if (myBird.getSizes().size() != 0)
				return true;
		
		return false;
	}
	public List<Bird> filterBeak(List<Bird> currentBirds) {
		List<Bird> filterBirds = filter(Filters.Beak, myBird.getBeak(), currentBirds, false);
		return filterBirds;
	}
	public List<Bird> filterSizes(List<Bird> currentBirds) {
		List<Bird> filterBirds = filter(Filters.Size, myBird.getSizes(), currentBirds, false);
		return filterBirds;
	}
	public List<Bird> filterColors(List<Bird> currentBirds) {
		List<Bird> filterBirds = filter(Filters.Color, myBird.getColors(), currentBirds, true);
		return filterBirds;
	}
	public List<Bird> filterSulhuette(List<Bird> currentBirds) {
		List<Bird> filterBirds = filter(Filters.Silhouette, myBird.getSilhouette(), currentBirds, false);
		return filterBirds;
	}
	// При желании все можно переделать в Object и фильтры будут работать
	private List<Bird> filter(Filters filterType, List<Integer> list, List<Bird> currentBirds, boolean and) {
		List<Bird> result = new ArrayList<Bird>();
		if (list == null || list.size() == 0) {
            Log.i("TAG", "Filter: " + filterType + " " + list + " result: " + currentBirds.size());
            return currentBirds;
        }
		List<Bird> tempResult;
		
		if (and){
			result = new ArrayList<Bird>(currentBirds);
		}
		
		for (Integer filterParam : list) {
			if (and) {
				tempResult = filter(filterType, filterParam, result);
				result = tempResult;
			} else {
				tempResult = filter(filterType, filterParam, currentBirds);
				result.addAll(tempResult);
			}
		}
        Log.i("TAG", "Filter: " + filterType + " " + list + " result: " + result.size());
		return result;
	}
	private List<Bird> filter(Filters filterType, Integer filterParam, List<Bird> currentBirds) {
		List<Bird> result = new ArrayList<Bird>();
		if (filterParam == null)
			return currentBirds;
		
		filterParam++;
		
		for (Bird bird : currentBirds) {
            if(bird.getName().equalsIgnoreCase("Middelste Zaagbek")) {
                Log.i("TAG", "Bird found");
            }
			List<Integer> params = new ArrayList<Integer>();
			switch (filterType){
			case Beak: 		 params = new ArrayList<Integer>(bird.getBeak());
				break;
			case Color:		 params = new ArrayList<Integer>(bird.getColors());
				break;
			case Silhouette: params = new ArrayList<Integer>(bird.getSilhouette());
				break;
			case Size:       params = new ArrayList<Integer>(bird.getSizes());
				break;
			}
			
			for (Integer param : params){
				if (param == filterParam){
					result.add(bird);
				}
			}
			
		}
		return result;
	}
	public ArrayList<Bird> sortBirds(List<Bird> sortingArray) {
		//List<Bird> result = new ArrayList(sortingArray);
		ArrayList<Bird> result = new ArrayList<Bird>();
		for(int i = 0; i < sortingArray.size(); i++){
			Bird curBird = sortingArray.get(i);
			if (result.contains(curBird)){
				result.remove(curBird);
				result.add(0, curBird);
			} else {
				result.add(curBird);
			}
		}
		
		return result;
	}
	
	public List<Bird> sortBirdsWithChance(List<Bird> sortingArray) {
		
		for (int i = 0; i < sortingArray.size(); i++){
			Bird curBird = sortingArray.get(i);
			for (int j = i + 1; j < sortingArray.size(); j++){
				Bird bird = sortingArray.get(j);
				Log.i("sort","curBird id =" + curBird.getId() + " bird id =" + bird.getId()+ " , i = "+i+"; j ="+j);
				if (curBird.getId() == bird.getId()){
					sortingArray.remove(j);
					j--;
				}
			}	
		}
		
		return sortingArray;
	}
}