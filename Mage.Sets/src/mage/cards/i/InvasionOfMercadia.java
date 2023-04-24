package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfMercadia extends CardImpl {

    public InvasionOfMercadia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{1}{R}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.k.KyrenFlamewright.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Mercadia enters the battlefield, you may discard a card. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())));
    }

    private InvasionOfMercadia(final InvasionOfMercadia card) {
        super(card);
    }

    @Override
    public InvasionOfMercadia copy() {
        return new InvasionOfMercadia(this);
    }
}
