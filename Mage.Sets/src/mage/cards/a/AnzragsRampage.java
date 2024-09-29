package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * @author Cguy7777
 */
public final class AnzragsRampage extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    private static final ValueHint hint = new ValueHint(
            "Artifacts put into graveyards from the battlefield this turn", AnzragsRampageValue.instance);

    public AnzragsRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Destroy all artifacts you don't control, then exile the top X cards of your library, where X is the number of artifacts that were put into graveyards from the battlefield this turn.
        // You may put a creature card exiled this way onto the battlefield. It gains haste. Return it to your hand at the beginning of the next end step.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        this.getSpellAbility().addEffect(new AnzragsRampageEffect().concatBy(", then"));
        this.getSpellAbility().addWatcher(new AnzragsRampageWatcher());
        this.getSpellAbility().addHint(hint);
    }

    private AnzragsRampage(final AnzragsRampage card) {
        super(card);
    }

    @Override
    public AnzragsRampage copy() {
        return new AnzragsRampage(this);
    }
}

class AnzragsRampageWatcher extends Watcher {

    private int artifactsDied;

    AnzragsRampageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent() && zEvent.getTarget().isArtifact(game)) {
                artifactsDied++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        artifactsDied = 0;
    }

    public int getArtifactsDied() {
        return artifactsDied;
    }
}

enum AnzragsRampageValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AnzragsRampageWatcher watcher = game.getState().getWatcher(AnzragsRampageWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher.getArtifactsDied();
    }

    @Override
    public AnzragsRampageValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class AnzragsRampageEffect extends OneShotEffect {

    AnzragsRampageEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile the top X cards of your library, where X is the number of artifacts " +
                "that were put into graveyards from the battlefield this turn. " +
                "You may put a creature card exiled this way onto the battlefield. It gains haste. " +
                "Return it to your hand at the beginning of the next end step";
    }

    private AnzragsRampageEffect(final AnzragsRampageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Exile the top X cards of your library,
        // where X is the number of artifacts that were put into graveyards from the battlefield this turn.
        Cards cards = new CardsImpl(controller.getLibrary()
                .getTopCards(game, AnzragsRampageValue.instance.calculate(game, source, this)));
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.createObjectRealtedWindowTitle(source, game, null));

        // You may put a creature card exiled this way onto the battlefield.
        TargetCard targetCard = new TargetCardInExile(
                0, 1, StaticFilters.FILTER_CARD_CREATURE, CardUtil.getExileZoneId(game, source));
        targetCard.withNotTarget(true);
        controller.choose(outcome, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return true;
        }

        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent == null) {
                return true;
            }

            // It gains haste.
            ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            hasteEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(hasteEffect, source);

            // Return it to your hand at the beginning of the next end step.
            ReturnToHandTargetEffect returnToHandEffect = new ReturnToHandTargetEffect();
            returnToHandEffect.setText("return it to your hand");
            returnToHandEffect.setTargetPointer(new FixedTarget(permanent, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToHandEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }

    @Override
    public AnzragsRampageEffect copy() {
        return new AnzragsRampageEffect(this);
    }
}
