package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.common.FirstXSpellCastThisTurnWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class ZimoneInfiniteAnalyst extends CardImpl {

    public ZimoneInfiniteAnalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // The first spell you cast with {X} in its mana cost each turn costs {1} less to cast for each +1/+1 counter on Zimone.
        this.addAbility(new SimpleStaticAbility(new ZimoneCostReductionEffect()), new FirstXSpellCastThisTurnWatcher());

        // Whenever you cast your first spell with {X} in its mana cost each turn, put two +1/+1 counters on Zimone.
        this.addAbility(new ZimoneTriggeredAbility());
    }

    private ZimoneInfiniteAnalyst(final ZimoneInfiniteAnalyst card) {
        super(card);
    }

    @Override
    public ZimoneInfiniteAnalyst copy() {
        return new ZimoneInfiniteAnalyst(this);
    }
}

class ZimoneTriggeredAbility extends TriggeredAbilityImpl {

    ZimoneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false);
    }

    private ZimoneTriggeredAbility(final ZimoneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        FirstXSpellCastThisTurnWatcher watcher = game.getState().getWatcher(FirstXSpellCastThisTurnWatcher.class);
        return watcher != null && spell.getId().equals(watcher.getFirstXSpellId(getControllerId()));
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell with {X} in its mana cost each turn, put two +1/+1 counters on {this}.";
    }

    @Override
    public ZimoneTriggeredAbility copy() {
        return new ZimoneTriggeredAbility(this);
    }
}

class ZimoneCostReductionEffect extends CostModificationEffectImpl {

    ZimoneCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "The first spell you cast with {X} in its mana cost each turn costs {1} less to cast for each +1/+1 counter on {this}";
    }

    private ZimoneCostReductionEffect(final ZimoneCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            int counters = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (counters > 0) {
                CardUtil.reduceCost(abilityToModify, counters);
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        if (spellCard == null || !spellCard.getManaCost().containsX()) {
            return false;
        }
        FirstXSpellCastThisTurnWatcher watcher = game.getState().getWatcher(FirstXSpellCastThisTurnWatcher.class);
        return watcher != null && watcher.getFirstXSpellId(source.getControllerId()) == null;
    }

    @Override
    public ZimoneCostReductionEffect copy() {
        return new ZimoneCostReductionEffect(this);
    }
}
