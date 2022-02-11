package mage.cards.c;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaplainOfAlms extends CardImpl {

    public ChaplainOfAlms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.c.ChapelShieldgeist.class;

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Disturb {3}{W}
        this.addAbility(new DisturbAbility(this, "{3}{W}"));
    }

    private ChaplainOfAlms(final ChaplainOfAlms card) {
        super(card);
    }

    @Override
    public ChaplainOfAlms copy() {
        return new ChaplainOfAlms(this);
    }
}
