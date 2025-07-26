package mage.cards.j;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.MayExileCardFromHandPlottedEffect;
import mage.abilities.effects.common.ruleModifying.CantCastDuringFirstThreeTurnsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JaceReawakened extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland card with mana value 3 or less");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public JaceReawakened(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(3);

        // You can't cast this spell during your first, second, or third turns of the game.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantCastDuringFirstThreeTurnsEffect()));

        // +1: Draw a card, then discard a card.
        this.addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(1, 1), 1));

        // +1: You may exile a nonland card with mana value 3 or less from your hand. If you do, it becomes plotted.
        this.addAbility(new LoyaltyAbility(new MayExileCardFromHandPlottedEffect(filter), 1));

        // -6: Until end of turn, whenever you cast a spell, copy it. You may choose new targets for the copy.
        this.addAbility(new LoyaltyAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new JaceReawakenedDelayedTriggeredAbility()
                ), -6
        ));
    }

    private JaceReawakened(final JaceReawakened card) {
        super(card);
    }

    @Override
    public JaceReawakened copy() {
        return new JaceReawakened(this);
    }
}

class JaceReawakenedDelayedTriggeredAbility extends DelayedTriggeredAbility {

    JaceReawakenedDelayedTriggeredAbility() {
        super(new CopyTargetStackObjectEffect(true), Duration.EndOfTurn, false);
    }

    private JaceReawakenedDelayedTriggeredAbility(final JaceReawakenedDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JaceReawakenedDelayedTriggeredAbility copy() {
        return new JaceReawakenedDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever you cast a spell, copy it. You may choose new targets for the copy.";
    }
}
