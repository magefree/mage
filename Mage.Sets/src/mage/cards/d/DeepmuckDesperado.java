package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeepmuckDesperado extends CardImpl {

    public DeepmuckDesperado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HOMARID);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you commit a crime, each opponent mills three cards. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                new MillCardsEachPlayerEffect(3, TargetController.OPPONENT)
        ).setTriggersLimitEachTurn(1));
    }

    private DeepmuckDesperado(final DeepmuckDesperado card) {
        super(card);
    }

    @Override
    public DeepmuckDesperado copy() {
        return new DeepmuckDesperado(this);
    }
}
