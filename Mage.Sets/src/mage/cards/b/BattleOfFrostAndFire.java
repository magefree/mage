package mage.cards.b;

import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author weirddan455
 */
public final class BattleOfFrostAndFire extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("non-Giant creature and each planeswalker");

    static {
        filter.add(Predicates.or(
                Predicates.and(CardType.CREATURE.getPredicate(), Predicates.not(SubType.GIANT.getPredicate())),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public BattleOfFrostAndFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Battle of Frost and Fire deals 4 damage to each non-Giant creature and each planeswalker.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DamageAllEffect(4, filter));

        // II — Scry 3.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ScryEffect(3));

        // III — Whenever you cast a spell with converted mana cost 5 or greater this turn, draw two cards, then discard a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new CreateDelayedTriggeredAbilityEffect(
                new BattleOfFrostAndFireTriggeredAbility(), false
        ));
        this.addAbility(sagaAbility);
    }

    private BattleOfFrostAndFire(final BattleOfFrostAndFire card) {
        super(card);
    }

    @Override
    public BattleOfFrostAndFire copy() {
        return new BattleOfFrostAndFire(this);
    }
}

class BattleOfFrostAndFireTriggeredAbility extends DelayedTriggeredAbility {

    public BattleOfFrostAndFireTriggeredAbility() {
        super(new DrawDiscardControllerEffect(2, 1), Duration.EndOfTurn, false);
        setTriggerPhrase("Whenever you cast a spell with mana value 5 or greater this turn, ");
    }

    private BattleOfFrostAndFireTriggeredAbility(BattleOfFrostAndFireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BattleOfFrostAndFireTriggeredAbility copy() {
        return new BattleOfFrostAndFireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            return spell != null && spell.getManaValue() >= 5;
        }
        return false;
    }
}
