
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RekindledFlame extends CardImpl {

    static final String rule = "if an opponent has no cards in hand, you may return Rekindled Flame from your graveyard to your hand";

    public RekindledFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Rekindled Flame deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // At the beginning of your upkeep, if an opponent has no cards in hand, you may return Rekindled Flame from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), TargetController.YOU, true
                ),
                new OpponentHasNoCardsInHandCondition(), rule);
        ability.setRuleVisible(true);
        this.addAbility(ability);

    }

    public RekindledFlame(final RekindledFlame card) {
        super(card);
    }

    @Override
    public RekindledFlame copy() {
        return new RekindledFlame(this);
    }
}

class OpponentHasNoCardsInHandCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null && opponent.getHand().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
