package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class WookieeMystic extends CardImpl {

    public WookieeMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.subtype.add(SubType.WOOKIEE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {R}, {G} or {W}. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it.
        Mana mana = Mana.RedMana(1);
        mana.setFlag(true);
        ManaEffect effect = new BasicManaEffect(mana);
        effect.setText("Add {R}. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher().withParams(ability));

        mana = Mana.GreenMana(1);
        mana.setFlag(true);
        effect = new BasicManaEffect(mana);
        effect.setText("Add {G}. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it");
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher().withParams(ability));

        mana = Mana.WhiteMana(1);
        mana.setFlag(true);
        effect = new BasicManaEffect(mana);
        effect.setText("Add {W}. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it");
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher().withParams(ability));
    }

    private WookieeMystic(final WookieeMystic card) {
        super(card);
    }

    @Override
    public WookieeMystic copy() {
        return new WookieeMystic(this);
    }
}

class WookieeMysticWatcher extends Watcher {

    private Ability source;
    private List<UUID> creatures = new ArrayList<>();

    public WookieeMysticWatcher() {
        super(WatcherScope.CARD);
    }

    Watcher withParams(Ability source) {
        this.source = source;
        return this;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature(game)
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
                Permanent creature = game.getPermanent(event.getSourceId());
                creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
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
