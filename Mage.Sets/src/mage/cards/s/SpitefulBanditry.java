package mage.cards.s;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpitefulBanditry extends CardImpl {

    public SpitefulBanditry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{R}{R}");

        // When Spiteful Banditry enters the battlefield, it deals X damage to each creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(
                ManacostVariableValue.ETB, StaticFilters.FILTER_PERMANENT_CREATURE
        )));

        // Whenever one or more creatures your opponents control die, you create a Treasure token. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new TreasureToken())
                        .setText("you create a Treasure token"),
                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setTriggerPhrase("Whenever one or more creatures your opponents control die, ").setTriggersOnceEachTurn(true));
    }

    private SpitefulBanditry(final SpitefulBanditry card) {
        super(card);
    }

    @Override
    public SpitefulBanditry copy() {
        return new SpitefulBanditry(this);
    }
}
