package mage.cards.h;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class HowlOfTheHorde extends CardImpl {

    public HowlOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");


        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy. 
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new HowlOfTheHordeDelayedTriggeredAbility());
        effect.setText("When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.");
        this.getSpellAbility().addEffect(effect);

        // <i>Raid</i> &mdash; If you attacked with a creature this turn, when you cast your next instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateDelayedTriggeredAbilityEffect(new HowlOfTheHordeDelayedTriggeredAbility()),
                RaidCondition.instance,
                "<br><br><i>Raid</i> &mdash; If you attacked this turn, when you cast your next instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy.")
        );
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
        this.getSpellAbility().addHint(RaidHint.instance);
    }

    private HowlOfTheHorde(final HowlOfTheHorde card) {
        super(card);
    }

    @Override
    public HowlOfTheHorde copy() {
        return new HowlOfTheHorde(this);
    }
}

class HowlOfTheHordeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    HowlOfTheHordeDelayedTriggeredAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    private HowlOfTheHordeDelayedTriggeredAbility(final HowlOfTheHordeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HowlOfTheHordeDelayedTriggeredAbility copy() {
        return new HowlOfTheHordeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isInstantOrSorcery(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.";
    }
}
