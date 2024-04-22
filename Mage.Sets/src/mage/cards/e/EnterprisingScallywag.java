package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnterprisingScallywag extends CardImpl {

    public EnterprisingScallywag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if you descended this turn, create a Treasure token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), TargetController.YOU,
                DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private EnterprisingScallywag(final EnterprisingScallywag card) {
        super(card);
    }

    @Override
    public EnterprisingScallywag copy() {
        return new EnterprisingScallywag(this);
    }
}
