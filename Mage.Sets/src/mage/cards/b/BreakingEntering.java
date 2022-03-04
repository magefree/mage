package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

public final class BreakingEntering extends SplitCard {

    public BreakingEntering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}", "{4}{B}{R}", SpellAbilityType.SPLIT_FUSED);

        // Breaking
        // Target player puts the top eight cards of their library into their graveyard.
        getLeftHalfCard().getSpellAbility().addEffect(new MillCardsTargetEffect(8));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // Entering
        // Put a creature card from a graveyard onto the battlefield under your control. It gains haste until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new EnteringReturnFromGraveyardToBattlefieldEffect());

    }

    private BreakingEntering(final BreakingEntering card) {
        super(card);
    }

    @Override
    public BreakingEntering copy() {
        return new BreakingEntering(this);
    }
}

class EnteringReturnFromGraveyardToBattlefieldEffect extends OneShotEffect {

    public EnteringReturnFromGraveyardToBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a creature card from a graveyard onto the battlefield under your control. It gains haste until end of turn.";
    }

    public EnteringReturnFromGraveyardToBattlefieldEffect(final EnteringReturnFromGraveyardToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public EnteringReturnFromGraveyardToBattlefieldEffect copy() {
        return new EnteringReturnFromGraveyardToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
