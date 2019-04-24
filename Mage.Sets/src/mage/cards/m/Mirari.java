
package mage.cards.m;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class Mirari extends CardImpl {

    public Mirari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        addSuperType(SuperType.LEGENDARY);

        // Whenever you cast an instant or sorcery spell, you may pay {3}. If you do, copy that spell. You may choose new targets for the copy.
        this.addAbility(new MirariTriggeredAbility());

    }

    public Mirari(final Mirari card) {
        super(card);
    }

    @Override
    public Mirari copy() {
        return new Mirari(this);
    }
}

class MirariTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    MirariTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CopyTargetSpellEffect(true), new GenericManaCost(3)), false);
        this.addTarget(new TargetSpell(filter));
    }

    MirariTriggeredAbility(final MirariTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MirariTriggeredAbility copy() {
        return new MirariTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isControlledInstantOrSorcery(spell)) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(spell.getId()));
                }
                return true;
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell) {
        return spell != null
                && (spell.isControlledBy(this.getControllerId()))
                && (spell.isInstant() || spell.isSorcery());
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell, you may pay {3}. If you do, copy that spell. You may choose new targets for the copy.";
    }
}
