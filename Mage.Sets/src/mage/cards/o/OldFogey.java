package mage.cards.o;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class OldFogey extends CardImpl {

    private static final FilterCard filter = new FilterCard("Homarids");
    private static final FilterControlledLandPermanent filter2 = new FilterControlledLandPermanent("snow-covered plains");

    static {
        filter.add(SubType.HOMARID.getPredicate());
        filter2.add(SuperType.SNOW.getPredicate());
        filter2.add(SubType.PLAINS.getPredicate());
    }

    public OldFogey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Phasing
        this.addAbility(PhasingAbility.getInstance());
        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
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

    private OldFogey(final OldFogey card) {
        super(card);
    }

    @Override
    public OldFogey copy() {
        return new OldFogey(this);
    }
}
