package mage.cards.h;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public final class HallOfTheBanditLord extends CardImpl {

    public HallOfTheBanditLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // Hall of the Bandit Lord enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}, Pay 3 life: Add {C}. If that mana is spent on a creature spell, it gains haste.
        Mana mana = Mana.ColorlessMana(1);
        mana.setFlag(true);
        ManaEffect effect = new BasicManaEffect(mana);
        effect.setText("Add {C}. If that mana is spent on a creature spell, it gains haste");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability, new HallOfTheBanditLordWatcher(ability));
    }

    private HallOfTheBanditLord(final HallOfTheBanditLord card) {
        super(card);
    }

    @Override
    public HallOfTheBanditLord copy() {
        return new HallOfTheBanditLord(this);
    }
}

class HallOfTheBanditLordWatcher extends Watcher {

    private final Ability source;
    private final List<UUID> creatures = new ArrayList<>();

    HallOfTheBanditLordWatcher(Ability source) {
        super(WatcherScope.CARD);
        this.source = source;
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
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
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
