package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarsWrath extends CardImpl {

    public AvatarsWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Choose up to one target creature, then airbend all other creatures.
        this.getSpellAbility().addEffect(new AvatarsWrathAirbendEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));

        // Until your next turn, your opponents can't cast spells from anywhere other than their hand.
        this.getSpellAbility().addEffect(new AvatarsWrathRuleEffect());

        // Exile Avatar's Wrath.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private AvatarsWrath(final AvatarsWrath card) {
        super(card);
    }

    @Override
    public AvatarsWrath copy() {
        return new AvatarsWrath(this);
    }
}

class AvatarsWrathAirbendEffect extends OneShotEffect {

    AvatarsWrathAirbendEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one target creature, then airbend all other creatures";
    }

    private AvatarsWrathAirbendEffect(final AvatarsWrathAirbendEffect effect) {
        super(effect);
    }

    @Override
    public AvatarsWrathAirbendEffect copy() {
        return new AvatarsWrathAirbendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game);
        Optional.ofNullable(game.getPermanent(getTargetPointer().getFirst(game, source)))
                .ifPresent(permanents::remove);
        return !permanents.isEmpty()
                && new AirbendTargetEffect()
                .setTargetPointer(new FixedTargets(permanents, game))
                .apply(game, source);
    }
}

class AvatarsWrathRuleEffect extends ContinuousRuleModifyingEffectImpl {

    AvatarsWrathRuleEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "<br>Until your next turn, your opponents can't cast spells from anywhere other than their hand.";
    }

    private AvatarsWrathRuleEffect(final AvatarsWrathRuleEffect effect) {
        super(effect);
    }

    @Override
    public AvatarsWrathRuleEffect copy() {
        return new AvatarsWrathRuleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
        return card != null && game.getState().getZone(card.getId()) != Zone.HAND;
    }
}
