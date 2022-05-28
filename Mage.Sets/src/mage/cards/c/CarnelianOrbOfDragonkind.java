package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreatureExploresTriggeredAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.c.CarnelianOrbOfDragonkindHasteEffect;
import mage.cards.c.CarnelianOrbOfDragonkindWatcher;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author AmeyMirchandani
 */
public final class CarnelianOrbOfDragonkind extends CardImpl {

    public CarnelianOrbOfDragonkind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        

        // {T}: Add {R}. If that mana is spent on a Dragon creature spell, it gains haste until end of turn.
        Mana mana = Mana.RedMana(1);
        mana.setFlag(true);
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.getEffects().get(0).setText("Add {R}. If that mana is spent on a Dragon creature spell, it gains haste until end of turn.");
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CarnelianOrbOfDragonkindHasteEffect()), new CarnelianOrbOfDragonkindWatcher());
    }

    private CarnelianOrbOfDragonkind(final CarnelianOrbOfDragonkind card) {
        super(card);
    }

    @Override
    public CarnelianOrbOfDragonkind copy() {
        return new CarnelianOrbOfDragonkind(this);
    }
}

class CarnelianOrbOfDragonkindWatcher extends Watcher {

    private List<UUID> creatures = new ArrayList<>();

    public CarnelianOrbOfDragonkindWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId()) && target != null && target.isCreature(game) && target.hasSubtype(SubType.DRAGON, game) && event.getFlag()) {
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

    public boolean creatureCastWithOrbsMana(UUID permanentId){
        return creatures.contains(permanentId);
    }

}

class CarnelianOrbOfDragonkindHasteEffect extends ContinuousEffectImpl {

    public CarnelianOrbOfDragonkindHasteEffect() {
        super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public CarnelianOrbOfDragonkindHasteEffect(final mage.cards.c.CarnelianOrbOfDragonkindHasteEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new mage.cards.c.CarnelianOrbOfDragonkindHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CarnelianOrbOfDragonkindWatcher watcher = game.getState().getWatcher(CarnelianOrbOfDragonkindWatcher.class, source.getSourceId());
        if (watcher != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
                if (watcher.creatureCastWithOrbsMana(perm.getId())) {
                    perm.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }

}
