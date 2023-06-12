package mage.abilities.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class SiegeAbility extends StaticAbility {

    public SiegeAbility() {
        super(Zone.ALL, null);
        this.addSubAbility(new TransformAbility());
        this.addSubAbility(new SiegeDefeatedTriggeredAbility());
    }

    private SiegeAbility(final SiegeAbility ability) {
        super(ability);
    }

    @Override
    public SiegeAbility copy() {
        return new SiegeAbility(this);
    }

    @Override
    public String getRule() {
        return "<i>(As a Siege enters, choose an opponent to protect it. You and others " +
                "can attack it. When it's defeated, exile it, then cast it transformed.)</i>";
    }
}

class SiegeDefeatedTriggeredAbility extends TriggeredAbilityImpl {

    SiegeDefeatedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SiegeDefeatedEffect());
        this.setRuleVisible(false);
    }

    private SiegeDefeatedTriggeredAbility(final SiegeDefeatedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SiegeDefeatedTriggeredAbility copy() {
        return new SiegeDefeatedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = getSourcePermanentOrLKI(game);
        return permanent != null
                && permanent.getCounters(game).getCount(CounterType.DEFENSE) == 0
                && event.getTargetId().equals(this.getSourceId())
                && event.getData().equals(CounterType.DEFENSE.getName()) && event.getAmount() > 0;
    }

    @Override
    public String getRule() {
        return "When the last defense counter is removed from this permanent, exile it, " +
                "then you may cast it transformed without paying its mana cost.";
    }
}

class SiegeDefeatedEffect extends OneShotEffect {

    SiegeDefeatedEffect() {
        super(Outcome.Benefit);
    }

    private SiegeDefeatedEffect(final SiegeDefeatedEffect effect) {
        super(effect);
    }

    @Override
    public SiegeDefeatedEffect copy() {
        return new SiegeDefeatedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (card == null || card.getSecondFaceSpellAbility() == null) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getSecondCardFace().getId(), Boolean.TRUE);
        if (player.cast(card.getSecondFaceSpellAbility(), game, true, new ApprovingObject(source, game))) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
            game.addEffect(new SiegeTransformEffect().setTargetPointer(new FixedTarget(card, game)), source);
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getSecondCardFace().getId(), null);
        return true;
    }
}

class SiegeTransformEffect extends ContinuousEffectImpl {

    public SiegeTransformEffect() {
        super(Duration.WhileOnStack, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
    }

    private SiegeTransformEffect(final SiegeTransformEffect effect) {
        super(effect);
    }

    @Override
    public SiegeTransformEffect copy() {
        return new SiegeTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null || spell.getCard().getSecondCardFace() == null) {
            return false;
        }

        // simulate another side as new card (another code part in spell constructor)
        TransformAbility.transformCardSpellDynamic(spell, spell.getCard().getSecondCardFace(), game);
        return true;
    }
}
