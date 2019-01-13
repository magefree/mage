package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FadingAbility;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author L_J
 */
public final class OldFogey extends CardImpl {

    private static final FilterCard filter = new FilterCard("Homarids");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("snow-covered plains");

    static {
        filter.add(new SubtypePredicate(SubType.HOMARID));
        filter2.add(new SupertypePredicate(SuperType.SNOW));
        filter2.add(new SubtypePredicate(SubType.PLAINS));
    }

    public OldFogey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Phasing
        this.addAbility(PhasingAbility.getInstance());
        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));
        // Echo {G}{G}
        this.addAbility(new EchoAbility("{G}{G}"));
        // Fading 3
        this.addAbility(new FadingAbility(3, this, true));
        // Bands with other Dinosaurs
        this.addAbility(new BandsWithOtherAbility(SubType.DINOSAUR));
        // Protection from Homarids
        this.addAbility(new ProtectionAbility(filter));
        // Snow-covered plainswalk
        this.addAbility(new LandwalkAbility(filter2));
        // Flanking
        this.addAbility(new FlankingAbility());
        // Rampage 2
        this.addAbility(new RampageAbility(2, true));
    }

    public OldFogey(final OldFogey card) {
        super(card);
    }

    @Override
    public OldFogey copy() {
        return new OldFogey(this);
    }
}
