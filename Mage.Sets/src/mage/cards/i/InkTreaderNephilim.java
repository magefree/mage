
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterInPlay;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public final class InkTreaderNephilim extends CardImpl {

    public InkTreaderNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}{U}");
        this.subtype.add(SubType.NEPHILIM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts an instant or sorcery spell, if that spell targets only Ink-Treader Nephilim, copy the spell for each other creature that spell could target. Each copy targets a different one of those creatures.
        this.addAbility(new InkTreaderNephilimTriggeredAbility());
    }

    public InkTreaderNephilim(final InkTreaderNephilim card) {
        super(card);
    }

    @Override
    public InkTreaderNephilim copy() {
        return new InkTreaderNephilim(this);
    }
}

class InkTreaderNephilimTriggeredAbility extends TriggeredAbilityImpl {

    InkTreaderNephilimTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InkTreaderNephilimEffect(), false);
    }

    InkTreaderNephilimTriggeredAbility(final InkTreaderNephilimTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InkTreaderNephilimTriggeredAbility copy() {
        return new InkTreaderNephilimTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null
                && (spell.isInstant() || spell.isSorcery())) {
            for (Effect effect : getEffects()) {
                effect.setValue("triggeringSpell", spell);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = (Spell) getEffects().get(0).getValue("triggeringSpell");
        if (spell != null) {
            boolean allTargetsInkTreaderNephilim = true;
            boolean atLeastOneTargetsInkTreaderNephilim = false;
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                Target targetInstance = addr.getTarget(spell);
                for (UUID target : targetInstance.getTargets()) {
                    allTargetsInkTreaderNephilim &= target.equals(sourceId);
                    atLeastOneTargetsInkTreaderNephilim |= target.equals(sourceId);
                }
            }
            if (allTargetsInkTreaderNephilim && atLeastOneTargetsInkTreaderNephilim) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell, if that spell targets only {this}, copy the spell for each other creature that spell could target. Each copy targets a different one of those creatures.";
    }
}

class InkTreaderNephilimEffect extends CopySpellForEachItCouldTargetEffect<Permanent> {

    public InkTreaderNephilimEffect() {
        this(new FilterCreaturePermanent());
    }

    public InkTreaderNephilimEffect(InkTreaderNephilimEffect effect) {
        super(effect);
    }

    private InkTreaderNephilimEffect(FilterInPlay<Permanent> filter) {
        super(filter);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected Spell getSpell(Game game, Ability source) {
        return (Spell) getValue("triggeringSpell");
    }

    @Override
    protected boolean changeTarget(Target target, Game game, Ability source) {
        return true;
    }

    @Override
    protected void modifyCopy(Spell copy, Game game, Ability source) {
        copy.setControllerId(source.getControllerId());
    }

    @Override
    public InkTreaderNephilimEffect copy() {
        return new InkTreaderNephilimEffect(this);
    }
}
