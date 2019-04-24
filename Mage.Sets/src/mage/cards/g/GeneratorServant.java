
package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author Quercitron
 */
public final class GeneratorServant extends CardImpl {

    public GeneratorServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}, Sacrifice Generator Servant: Add {C}{C}.  If that mana is spent on a creature spell, it gains haste until end of turn.
        Mana mana = Mana.ColorlessMana(2);
        mana.setFlag(true); // used to indicate this mana ability
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.getEffects().get(0).setText("Add {C}{C}. If that mana is spent on a creature spell, it gains haste until end of turn.");
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GeneratorServantHasteEffect()), new GeneratorServantWatcher());
    }

    public GeneratorServant(final GeneratorServant card) {
        super(card);
    }

    @Override
    public GeneratorServant copy() {
        return new GeneratorServant(this);
    }
}

class GeneratorServantWatcher extends Watcher {

    public List<UUID> creatures = new ArrayList<>();

    public GeneratorServantWatcher() {
        super(GeneratorServantWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    public GeneratorServantWatcher(final GeneratorServantWatcher watcher) {
        super(watcher);
        this.creatures.addAll(watcher.creatures);
    }

    @Override
    public GeneratorServantWatcher copy() {
        return new GeneratorServantWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId()) && target != null && target.isCreature() && event.getFlag()) {
                if (target instanceof Spell) {
                    this.creatures.add(((Spell) target).getCard().getId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatures.clear();
    }

}

class GeneratorServantHasteEffect extends ContinuousEffectImpl {

    public GeneratorServantHasteEffect() {
        super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public GeneratorServantHasteEffect(final GeneratorServantHasteEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new GeneratorServantHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GeneratorServantWatcher watcher = (GeneratorServantWatcher) game.getState().getWatchers().get(GeneratorServantWatcher.class.getSimpleName(), source.getSourceId());
        if (watcher != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
                if (watcher.creatures.contains(perm.getId())) {
                    perm.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }

}
