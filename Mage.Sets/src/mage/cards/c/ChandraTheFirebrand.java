
package mage.cards.c;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class ChandraTheFirebrand extends CardImpl {

    public ChandraTheFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(3);

        // +1: Chandra, the Firebrand deals 1 damage to any target.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(1), 1);
        ability1.addTarget(new TargetAnyTarget());
        this.addAbility(ability1);

        // -2: When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new ChandraTheFirebrandAbility());
        effect.setText("When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy");
        this.addAbility(new LoyaltyAbility(effect, -2));

        // -6: Chandra, the Firebrand deals 6 damage to each of up to six target creatures and/or players
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(6, true, "each of up to six targets"), -6);
        ability2.addTarget(new TargetAnyTarget(0, 6));
        this.addAbility(ability2);
    }

    private ChandraTheFirebrand(final ChandraTheFirebrand card) {
        super(card);
    }

    @Override
    public ChandraTheFirebrand copy() {
        return new ChandraTheFirebrand(this);
    }

}

class ChandraTheFirebrandAbility extends DelayedTriggeredAbility {

    ChandraTheFirebrandAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    ChandraTheFirebrandAbility(final ChandraTheFirebrandAbility ability) {
        super(ability);
    }

    @Override
    public ChandraTheFirebrandAbility copy() {
        return new ChandraTheFirebrandAbility(this);
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
