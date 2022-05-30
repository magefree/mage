
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

        // {T}, Sacrifice Generator Servant: Add {C}{C}. If that mana is spent on a creature spell, it gains haste until end of turn.
        Mana mana = Mana.ColorlessMana(2);
        mana.setFlag(true); // used to indicate this mana ability
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.getEffects().get(0).setText("Add {C}{C}. If that mana is spent on a creature spell, it gains haste until end of turn.");
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GeneratorServantHasteEffect()), new GeneratorServantWatcher());
    }

    private GeneratorServant(final GeneratorServant card) {
        super(card);
    }

    @Override
    public GeneratorServant copy() {
        return new GeneratorServant(this);
    }
}

class GeneratorServantWatcher extends Watcher {

    private List<UUID> creatures = new ArrayList<>();

    public GeneratorServantWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.MANA_PAID) {
            return;
        }

        MageObject target = game.getObject(event.getTargetId());
        if (target == null || !(target instanceof Spell)) {
            return;
        }

        // Mana from Generator Servant
        if (!event.getFlag()) {
            return;
        }

        if (event.getSourceId() == null || !event.getSourceId().equals(this.getSourceId())) {
            return;
        }

        if (target.isCreature(game)) {
            this.creatures.add(((Spell) target).getCard().getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatures.clear();
    }

    public boolean creatureCastWithServantsMana(UUID permanentId){
        return creatures.contains(permanentId);
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
        GeneratorServantWatcher watcher = game.getState().getWatcher(GeneratorServantWatcher.class, source.getSourceId());
        if (watcher == null) {
            return false;
        }

        for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
            if (watcher.creatureCastWithServantsMana(perm.getId())) {
                perm.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
            }
        }
        return true;
    }
}
