package mage.cards.d;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author htrajan
 */
public final class DoubleVision extends CardImpl {

    public DoubleVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever you cast your first instant or sorcery spell each turn, copy that spell. You may choose new targets for the copy.
        this.addAbility(new DoubleVisionCopyTriggeredAbility());
    }

    private DoubleVision(final DoubleVision card) {
        super(card);
    }

    @Override
    public DoubleVision copy() {
        return new DoubleVision(this);
    }
}

class DoubleVisionCopyTriggeredAbility extends SpellCastControllerTriggeredAbility {

    DoubleVisionCopyTriggeredAbility() {
        super(new CopyTargetSpellEffect(true), new FilterInstantOrSorcerySpell(), false);
    }

    private DoubleVisionCopyTriggeredAbility(final DoubleVisionCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DoubleVisionCopyTriggeredAbility copy() {
        return new DoubleVisionCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isFirstInstantOrSorceryCastByPlayerOnTurn(spell, game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                return true;
            }
        }
        return false;
    }

    private boolean isFirstInstantOrSorceryCastByPlayerOnTurn(Spell spell, Game game) {
        if (spell != null) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> eligibleSpells = watcher.getSpellsCastThisTurn(this.getControllerId())
                        .stream()
                        .filter(spell1 -> spell1.isInstantOrSorcery(game))
                        .collect(Collectors.toList());
                return eligibleSpells.size() == 1 && eligibleSpells.get(0).getId().equals(spell.getId());
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first instant or sorcery spell each turn, copy that spell. You may choose new targets for the copy.";
    }
}
