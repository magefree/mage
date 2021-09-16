package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurlyBreaker extends CardImpl {

    public BurlyBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.d.DireStrainDemolisher.class;

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private BurlyBreaker(final BurlyBreaker card) {
        super(card);
    }

    @Override
    public BurlyBreaker copy() {
        return new BurlyBreaker(this);
    }
}
