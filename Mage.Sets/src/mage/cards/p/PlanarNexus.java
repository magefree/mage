package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanarNexus extends CardImpl {

    public PlanarNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Planar Nexus is every nonbasic land type.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new PlanarNexusEffect()));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private PlanarNexus(final PlanarNexus card) {
        super(card);
    }

    @Override
    public PlanarNexus copy() {
        return new PlanarNexus(this);
    }

    @Override
    public boolean hasSubTypeForDeckbuilding(SubType subType) {
        return subType.getSubTypeSet() == SubTypeSet.NonBasicLandType || super.hasSubTypeForDeckbuilding(subType);
    }
}

class PlanarNexusEffect extends ContinuousEffectImpl {

    PlanarNexusEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} is every nonbasic land type. " +
                "<i>(Nonbasic land types include Cave, Desert, Gate, Lair, " +
                "Locus, Mine, Power-Plant, Sphere, Tower, and Urza's.)</i>";
    }

    private PlanarNexusEffect(final PlanarNexusEffect effect) {
        super(effect);
    }

    @Override
    public PlanarNexusEffect copy() {
        return new PlanarNexusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null) {
            return false;
        }
        sourceObject.setIsAllNonbasicLandTypes(game, true);
        return true;
    }
}
