package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BelovedBeggar extends CardImpl {

    public BelovedBeggar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.g.GenerousSoul.class;

        // Disturb {4}{W}{W}
        this.addAbility(new DisturbAbility(this, "{4}{W}{W}"));
    }

    private BelovedBeggar(final BelovedBeggar card) {
        super(card);
    }

    @Override
    public BelovedBeggar copy() {
        return new BelovedBeggar(this);
    }
}
