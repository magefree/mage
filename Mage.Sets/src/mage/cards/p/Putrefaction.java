
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J (based on dustinconrad)
 */
public final class Putrefaction extends CardImpl {

    public Putrefaction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");

        // Whenever a player casts a green or white spell, that player discards a card.
        this.addAbility(new PutrefactionTriggeredAbility());
    }

    private Putrefaction(final Putrefaction card) {
        super(card);
    }

    @Override
    public Putrefaction copy() {
        return new Putrefaction(this);
    }
}

class PutrefactionTriggeredAbility extends SpellCastAllTriggeredAbility {

    private static final FilterSpell filterGreenOrWhiteSpell = new FilterSpell("green or white spell");
    static {
        filterGreenOrWhiteSpell.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE)));
    }

    public PutrefactionTriggeredAbility() {
        super(new DiscardTargetEffect(1), filterGreenOrWhiteSpell, false);
    }

    public PutrefactionTriggeredAbility(PutrefactionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filterGreenOrWhiteSpell.match(spell, getControllerId(), this, game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public PutrefactionTriggeredAbility copy() {
        return new PutrefactionTriggeredAbility(this);
    }
}
