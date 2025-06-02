package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ReplicateAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.*;


/**
 * @author LevelX2, Alex-Vasile
 */
public class EachSpellYouCastHasReplicateEffect extends ContinuousEffectImpl {

    private final FilterSpell filter;
    private final Cost fixedNewCost;
    private final Map<UUID, ReplicateAbility> replicateAbilities = new HashMap<>();

    public EachSpellYouCastHasReplicateEffect(FilterSpell filter, String reminderText) {
        this(filter, reminderText, null);
    }

    /**
     * @param filter        Filter used for filtering spells
     * @param reminderText  Reminder text that will be italicized and added (capitalize and include punctuation)
     * @param fixedNewCost  Fixed new cost to pay as the replication cost
     */
    public EachSpellYouCastHasReplicateEffect(FilterSpell filter, String reminderText, Cost fixedNewCost) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.filter = filter;
        this.fixedNewCost = fixedNewCost;
        this.staticText = "Each " + this.filter.getMessage() + (this.filter.getMessage().contains("cast") ? "" : " you cast") +
                " has replicate" +
                (this.fixedNewCost == null ? ". The replicate cost is equal to its mana cost" : ' ' + this.fixedNewCost.getText())
                + ((reminderText != null && !reminderText.isEmpty()) ? (". <i>(" + reminderText + ")</i>") : "");
    }

    private EachSpellYouCastHasReplicateEffect(final EachSpellYouCastHasReplicateEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.fixedNewCost = effect.fixedNewCost;
        this.replicateAbilities.putAll(effect.replicateAbilities);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            replicateAbilities.clear();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Spell)) {
                continue;
            }
            Spell spell = (Spell) object;
            Cost cost = fixedNewCost != null ? fixedNewCost.copy() : spell.getSpellAbility().getManaCosts().copy();
            ReplicateAbility replicateAbility = replicateAbilities.computeIfAbsent(spell.getId(), k -> new ReplicateAbility(cost));
            game.getState().addOtherAbility(spell.getCard(), replicateAbility, false); // Do not copy because paid and # of activations state is handled in the ability
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null
                || !permanent.isControlledBy(source.getControllerId())) { // Verify that the controller of the permanent is the one who cast the spell
            return Collections.emptyList();
        }

        ArrayList<MageItem> items = new ArrayList<>();
        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell)
                    || stackObject.isCopy()
                    || !stackObject.isControlledBy(source.getControllerId())
                    || (fixedNewCost == null && stackObject.getManaCost().isEmpty())) { // If the spell has no mana cost, it cannot be played by this ability unless a fixed alternative cost (e.g. such as from Threefold Signal) is specified.
                continue;
            }
            if (filter.match(stackObject, game)) {
                items.add(stackObject);
            }
        }
        return items;
    }

    @Override
    public EachSpellYouCastHasReplicateEffect copy() {
        return new EachSpellYouCastHasReplicateEffect(this);
    }
}
