package mage.cards.t;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinbladeGeist extends CardImpl {

    public TwinbladeGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.t.TwinbladeInvocation.class;

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Disturb {2}{W}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{2}{W}")));
    }

    private TwinbladeGeist(final TwinbladeGeist card) {
        super(card);
    }

    @Override
    public TwinbladeGeist copy() {
        return new TwinbladeGeist(this);
    }
}
