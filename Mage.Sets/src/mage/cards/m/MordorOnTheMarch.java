package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class MordorOnTheMarch extends CardImpl {

    public MordorOnTheMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Exile a creature card from your graveyard. Create a token that's a copy of it. It gains haste until end of turn. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new MordorOnTheMarchEffect());

        // Storm
        this.addAbility(new StormAbility());

    }

    private MordorOnTheMarch(final MordorOnTheMarch card) {
        super(card);
    }

    @Override
    public MordorOnTheMarch copy() {
        return new MordorOnTheMarch(this);
    }
}

class MordorOnTheMarchEffect extends OneShotEffect {

    public MordorOnTheMarchEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile a creature card from your graveyard. Create a token that's a copy of it. It gains haste until end of turn. Exile it at the beginning of the next end step";
    }

    private MordorOnTheMarchEffect(final MordorOnTheMarchEffect effect) {
        super(effect);
    }

    @Override
    public MordorOnTheMarchEffect copy() {
        return new MordorOnTheMarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.withNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return true;
        }
        controller.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false);
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1));
            controller.moveCards(card, Zone.EXILED, source, game);
            effect.apply(game, source);
            effect.getAddedPermanents().stream().forEach(permanent -> {
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(
                        HasteAbility.getInstance(), Duration.UntilYourNextTurn
                );
                continuousEffect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(continuousEffect, source);
            });
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }

        return false;
    }

}
