
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class EndlessWhispers extends CardImpl {

    public EndlessWhispers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Each creature has "When this creature dies, choose target opponent. That player puts this card from its owner's graveyard onto the battlefield under their control at the beginning of the next end step."
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceToBattlefieldEffect());
        Effect effect = new CreateDelayedTriggeredAbilityEffect(delayedAbility, true);
        effect.setText("choose target opponent. That player puts this card from its owner's graveyard onto the battlefield under their control at the beginning of the next end step");
        Ability gainAbility = new DiesSourceTriggeredAbility(effect);
        gainAbility.addTarget(new TargetOpponent());

        effect = new GainAbilityAllEffect(gainAbility, Duration.WhileOnBattlefield, new FilterCreaturePermanent("Each creature"));
        effect.setText("Each creature has \"When this creature dies, choose target opponent. That player puts this card from its owner's graveyard onto the battlefield under their control at the beginning of the next end step.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private EndlessWhispers(final EndlessWhispers card) {
        super(card);
    }

    @Override
    public EndlessWhispers copy() {
        return new EndlessWhispers(this);
    }
}

class ReturnSourceToBattlefieldEffect extends OneShotEffect {

    public ReturnSourceToBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "That player puts this card from its owner's graveyard onto the battlefield under their control";
    }

    private ReturnSourceToBattlefieldEffect(final ReturnSourceToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSourceToBattlefieldEffect copy() {
        return new ReturnSourceToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        Player player = game.getPlayer(source.getFirstTarget());

        if (player == null) {
            return false;
        }

        return player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
    }

}
