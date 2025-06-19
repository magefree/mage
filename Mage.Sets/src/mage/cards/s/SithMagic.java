package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class SithMagic extends CardImpl {

    public SithMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");

        // <i>Hate</i> &mdash; At the beggining of each combat, if opponent lost life from a source other than combat damage this turn, you may return target card from a graveyard to the battlefield under your control. It gains lifelink and haste. Exile it at the beginning of the next end step or if it would leave the battlefield.
        Ability ability = new BeginningOfCombatTriggeredAbility(TargetController.ANY, new SithMagicEffect(), true).withInterveningIf(HateCondition.instance);
        ability.addEffect(new SithMagicReplacementEffect());
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability.setAbilityWord(AbilityWord.HATE), new LifeLossOtherFromCombatWatcher());
    }

    private SithMagic(final SithMagic card) {
        super(card);
    }

    @Override
    public SithMagic copy() {
        return new SithMagic(this);
    }
}

class SithMagicEffect extends OneShotEffect {

    SithMagicEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return target card from a graveyard to the battlefield under your control. It gains lifelink and haste. Exile it at the beginning of the next end step or if it would leave the battlefield";
    }

    private SithMagicEffect(final SithMagicEffect effect) {
        super(effect);
    }

    @Override
    public SithMagicEffect copy() {
        return new SithMagicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent creature = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
                if (creature != null) {
                    // gains haste
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);
                    // gains lifelink
                    effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);
                    // Exile at begin of next end step
                    ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(new FixedTarget(creature, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }
}

class SithMagicReplacementEffect extends ReplacementEffectImpl {

    SithMagicReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "or if it would leave the battlefield";
    }

    private SithMagicReplacementEffect(final SithMagicReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SithMagicReplacementEffect copy() {
        return new SithMagicReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getFirstTarget())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED;
    }
}
