package mage.cards.c;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.*;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandPermanentCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

public class ConduitOfWorlds extends CardImpl {
    public ConduitOfWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}{G}");

        //You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(new PlayLandsFromGraveyardControllerEffect()));

        //{T}: Choose target nonland permanent card in your graveyard. If you haven't cast a spell this turn, you may
        //cast that card. If you do, you can't cast additional spells this turn. Activate only as a sorcery.
        ActivateAsSorceryConduitOfWorldsActivatedAbility activateAsSorceryConduitOfWorldsActivatedAbility =
                new ActivateAsSorceryConduitOfWorldsActivatedAbility(Zone.BATTLEFIELD, new ConduitOfWorldsEffect(),
                        new TapSourceCost(), new ConduitOfWorldsCondition());
        activateAsSorceryConduitOfWorldsActivatedAbility.addTarget(new TargetCardInYourGraveyard(new FilterNonlandPermanentCard()));
        this.addAbility(activateAsSorceryConduitOfWorldsActivatedAbility);
    }

    private ConduitOfWorlds(final ConduitOfWorlds card) {
        super(card);
    }

    @Override
    public ConduitOfWorlds copy() {
        return new ConduitOfWorlds(this);
    }
}

class ConduitOfWorldsEffect extends OneShotEffect {

    public ConduitOfWorldsEffect() {
        super(Outcome.Benefit);
    }

    private ConduitOfWorldsEffect(final ConduitOfWorldsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null
                && controller.chooseUse(Outcome.Neutral, "Cast " + card.getLogName() + '?', source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, false),
                    game, false, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            ContinuousEffect effect = new ConduitOfWorldsReplacementEffect();
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public ConduitOfWorldsEffect copy() {
        return new ConduitOfWorldsEffect();
    }
}

class ConduitOfWorldsCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) < 1;
    }
}

class ActivateAsSorceryConduitOfWorldsActivatedAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    public ActivateAsSorceryConduitOfWorldsActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
        timing = TimingRule.SORCERY;
    }

    private ActivateAsSorceryConduitOfWorldsActivatedAbility(final ActivateAsSorceryConduitOfWorldsActivatedAbility ability) {
        super(ability);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ActivateAsSorceryConduitOfWorldsActivatedAbility copy() {
        return new ActivateAsSorceryConduitOfWorldsActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + "Choose target nonland permanent card in your graveyard. If you haven't cast a " +
                "spell this turn, you may cast that card. If you do, you can't cast additional spells this turn. " +
                "Activate only as a sorcery.";
    }
}

class ConduitOfWorldsReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public ConduitOfWorldsReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "If you do, you can't cast additional spells this turn.";
    }

    private ConduitOfWorldsReplacementEffect(final ConduitOfWorldsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ConduitOfWorldsReplacementEffect copy() {
        return new ConduitOfWorldsReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return card != null;
    }

}
