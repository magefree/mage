
package mage.cards.o;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Mitchel Stein
 */
public final class Opalescence extends CardImpl {

    public Opalescence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Each other non-Aura enchantment is a creature with power and toughness each equal to its converted mana cost. It's still an enchantment.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpalescenceEffect()));

    }

    public Opalescence(final Opalescence card) {
        super(card);
    }

    @Override
    public Opalescence copy() {
        return new Opalescence(this);
    }

}

class OpalescenceEffect extends ContinuousEffectImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Each other non-Aura enchantment");
    private static final EnumSet checkDependencyTypes;

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.AURA)));
        filter.add(new AnotherPredicate());
        checkDependencyTypes = EnumSet.of(DependencyType.AuraAddingRemoving, DependencyType.EnchantmentAddingRemoving);
    }

    public OpalescenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "Each other non-Aura enchantment is a creature in addition to its other types and has base power and base toughness each equal to its converted mana cost";
    }

    public OpalescenceEffect(final OpalescenceEffect effect) {
        super(effect);
    }

    @Override
    public OpalescenceEffect copy() {
        return new OpalescenceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (!permanent.isCreature()) {
                            permanent.addCardType(CardType.CREATURE);
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int manaCost = permanent.getConvertedManaCost();
                        permanent.getPower().setValue(manaCost);
                        permanent.getToughness().setValue(manaCost);
                    }
            }

        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
        Set<UUID> dependentTo = new HashSet<>();
        for (ContinuousEffect effect : allEffectsInLayer) {
            for (DependencyType dependencyType : effect.getDependencyTypes()) {
                if (checkDependencyTypes.contains(dependencyType)) {
                    dependentTo.add(effect.getId());
                }
            }
        }
        return dependentTo;
    }
}
