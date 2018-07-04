
package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class MelekIzzetParagon extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MelekIzzetParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));

        // You may cast the top card of your library if it's an instant or sorcery card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayTheTopCardEffect(filter)));

        // Whenever you cast an instant or sorcery spell from your library, copy it. You may choose new targets for the copy.
        this.addAbility(new MelekIzzetParagonTriggeredAbility());
    }

    public MelekIzzetParagon(final MelekIzzetParagon card) {
        super(card);
    }

    @Override
    public MelekIzzetParagon copy() {
        return new MelekIzzetParagon(this);
    }
}

class MelekIzzetParagonTriggeredAbility extends TriggeredAbilityImpl {

    public MelekIzzetParagonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), false);
    }

    public MelekIzzetParagonTriggeredAbility(final MelekIzzetParagonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MelekIzzetParagonTriggeredAbility copy() {
        return new MelekIzzetParagonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() == Zone.LIBRARY) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.isOwnedBy(super.getControllerId())
                    && (spell.isInstant()
                    || spell.isSorcery())) {
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
        return "Whenever you cast an instant or sorcery spell from your library, copy it. You may choose new targets for the copy.";
    }
}
