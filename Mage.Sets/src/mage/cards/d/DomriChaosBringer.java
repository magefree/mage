package mage.cards.d;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.DomriChaosBringerEmblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DomriChaosBringer extends CardImpl {

    public DomriChaosBringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOMRI);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Add {R} or {G}. If that mana is spent on a creature spell, it gains riot.
        // TODO: make this into a single ability, also make this, Generator Servant and Hall of the Bandit Lord work without card scope watchers
        Mana mana = Mana.RedMana(1);
        mana.setFlag(true);
        Ability ability = new LoyaltyAbility(new BasicManaEffect(mana).setText("Add {R}. If that mana is spent on a creature spell, it gains riot"), 1);
        this.addAbility(ability, new HallOfTheBanditLordWatcher(ability));
        mana = Mana.GreenMana(1);
        mana.setFlag(true);
        ability = new LoyaltyAbility(new BasicManaEffect(mana).setText("Add {G}. If that mana is spent on a creature spell, it gains riot"), 1);
        this.addAbility(ability, new HallOfTheBanditLordWatcher(ability));

        // −3: Look at the top four cards of your library. You may reveal up to two creature cards from among them and put them into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                new StaticValue(4), false, new StaticValue(2),
                StaticFilters.FILTER_CARD_CREATURE, Zone.LIBRARY, false,
                true, false, Zone.HAND, false, false, false
        ).setText(
                "Look at the top four cards of your library. " +
                        "You may reveal up to two creature cards from among them " +
                        "and put them into your hand. Put the rest on the bottom of your library " +
                        "in a random order."
        ), -3));

        // −8: You get an emblem with "At the beginning of each end step, create a 4/4 red and green Beast creature token with trample."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DomriChaosBringerEmblem()), -8));
    }

    private DomriChaosBringer(final DomriChaosBringer card) {
        super(card);
    }

    @Override
    public DomriChaosBringer copy() {
        return new DomriChaosBringer(this);
    }
}

class HallOfTheBanditLordWatcher extends Watcher {

    private final Ability source;
    private final List<UUID> creatures = new ArrayList<>();

    HallOfTheBanditLordWatcher(Ability source) {
        super(HallOfTheBanditLordWatcher.class.getSimpleName(), WatcherScope.CARD);
        this.source = source;
    }

    private HallOfTheBanditLordWatcher(final HallOfTheBanditLordWatcher watcher) {
        super(watcher);
        this.creatures.addAll(watcher.creatures);
        this.source = watcher.source;
    }

    @Override
    public HallOfTheBanditLordWatcher copy() {
        return new HallOfTheBanditLordWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature()
                    && event.getFlag()) {
                if (target instanceof Spell) {
                    this.creatures.add(((Spell) target).getCard().getId());
                }
            }
        }
        if (event.getType() == GameEvent.EventType.COUNTERED) {
            if (creatures.contains(event.getTargetId())) {
                creatures.remove(event.getSourceId());
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (creatures.contains(event.getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                // spell was e.g. exiled and goes again to stack, so previous cast has not resolved.
                if (zEvent.getToZone() == Zone.STACK) {
                    creatures.remove(event.getSourceId());
                }
            }
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (creatures.contains(event.getSourceId())) {
                ContinuousEffect effect = new GainAbilityTargetEffect(new RiotAbility(), Duration.Custom);
                effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                game.addEffect(effect, source);
                creatures.remove(event.getSourceId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatures.clear();
    }

}
