
package mage.cards.m;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class MarchOfTheMachines extends CardImpl {

    public MarchOfTheMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Each noncreature artifact is an artifact creature with power and toughness each equal to its converted mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MarchOfTheMachinesEffect()));
    }

    private MarchOfTheMachines(final MarchOfTheMachines card) {
        super(card);
    }

    @Override
    public MarchOfTheMachines copy() {
        return new MarchOfTheMachines(this);
    }
}

class MarchOfTheMachinesEffect extends ContinuousEffectImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public MarchOfTheMachinesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "Each noncreature artifact is an artifact creature with power and toughness each equal to its mana value";
        dependendToTypes.add(DependencyType.ArtifactAddingRemoving);
    }

    public MarchOfTheMachinesEffect(final MarchOfTheMachinesEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfTheMachinesEffect copy() {
        return new MarchOfTheMachinesEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    affectedObjectList.clear();
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                        if (permanent != null) {
                            affectedObjectList.add(new MageObjectReference(permanent, game));
                            permanent.addCardType(game, CardType.CREATURE);
                        }
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                        Permanent permanent = it.next().getPermanent(game);
                        if (permanent != null) {
                            int manaCost = permanent.getManaValue();
                            permanent.getPower().setModifiedBaseValue(manaCost);
                            permanent.getToughness().setModifiedBaseValue(manaCost);
                        }
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

}
