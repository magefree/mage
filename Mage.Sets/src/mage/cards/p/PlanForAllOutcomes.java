package mage.cards.p;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author muz
 */
public final class PlanForAllOutcomes extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PlanForAllOutcomes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        
        // When this enchantment enters, the owner of up to one other target nonland permanent puts it on their choice of the top or bottom of their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnTopOrBottomLibraryTargetEffect(true));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Whenever you cast your first noncreature spell each turn, empower Jace 1.
        this.addAbility(new PlanForAllOutcomesTriggeredAbility());
    }

    private PlanForAllOutcomes(final PlanForAllOutcomes card) {
        super(card);
    }

    @Override
    public PlanForAllOutcomes copy() {
        return new PlanForAllOutcomes(this);
    }
}


class PlanForAllOutcomesTriggeredAbility extends TriggeredAbilityImpl {

    PlanForAllOutcomesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EmpowerJaceEffect(1));
        setTriggerPhrase("Whenever you cast your first noncreature spell each turn, ");
    }

    private PlanForAllOutcomesTriggeredAbility(final PlanForAllOutcomesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlanForAllOutcomesTriggeredAbility copy() {
        return new PlanForAllOutcomesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> nonCreatureSpells = watcher
            .getSpellsCastThisTurn(this.getControllerId())
            .stream()
            .filter(Objects::nonNull)
            .filter(s -> !s.isCreature(game))
            .collect(Collectors.toList());
        return nonCreatureSpells.size() == 1 && nonCreatureSpells.get(0).getId().equals(event.getTargetId());
    }
}