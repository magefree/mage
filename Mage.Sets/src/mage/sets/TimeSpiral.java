package mage.sets;

import java.util.GregorianCalendar;
import java.util.List;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

public class TimeSpiral extends ExpansionSet {

    private static final TimeSpiral fINSTANCE =  new TimeSpiral();

    public static TimeSpiral getInstance() {
        return fINSTANCE;
    }

    private TimeSpiral() {
        super("Time Spiral", "TSP", "mage.sets.timespiral", new GregorianCalendar(2006, 9, 9).getTime(), SetType.EXPANSION);
        this.blockName = "Time Spiral";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }

    @Override
    public List<Card> createBooster() {
        List<Card> booster = super.createBooster();
        CardCriteria criteria = new CardCriteria();
        criteria.rarities(Rarity.SPECIAL).setCodes("TSB");
        addToBooster(booster, CardRepository.instance.findCards(criteria));
        return booster;
    }
}
