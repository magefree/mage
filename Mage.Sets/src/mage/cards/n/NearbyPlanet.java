package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NearbyPlanet extends CardImpl {

    public NearbyPlanet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Rangeling
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new NearbyPlanetEffect()));

        // Nearby Planet enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Nearby Planet enters the battlefield, sacrifice it unless you pay {1}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new GenericManaCost(1)).setText("sacrifice it unless you pay {1}")
        ));
    }

    private NearbyPlanet(final NearbyPlanet card) {
        super(card);
    }

    @Override
    public NearbyPlanet copy() {
        return new NearbyPlanet(this);
    }

    @Override
    public boolean hasSubTypeForDeckbuilding(SubType subType) {
        return subType.getSubTypeSet().isLand() || super.hasSubTypeForDeckbuilding(subType);
    }
}

class NearbyPlanetEffect extends ContinuousEffectImpl {

    private static final Ability[] basicManaAbilities = {
            new WhiteManaAbility(),
            new BlueManaAbility(),
            new BlackManaAbility(),
            new RedManaAbility(),
            new GreenManaAbility()
    };

    NearbyPlanetEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        dependendToTypes.add(DependencyType.BecomeMountain);
        dependendToTypes.add(DependencyType.BecomeForest);
        dependendToTypes.add(DependencyType.BecomeSwamp);
        dependendToTypes.add(DependencyType.BecomeIsland);
        dependendToTypes.add(DependencyType.BecomePlains);
        staticText = "Rangeling <i>(This card is every land type, including Plains, Island, Swamp, " +
                "Mountain, Forest, Desert, Gate, Lair, Locus, and all those Urza's ones.)</i>";
    }

    private NearbyPlanetEffect(final NearbyPlanetEffect effect) {
        super(effect);
    }

    @Override
    public NearbyPlanetEffect copy() {
        return new NearbyPlanetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null) {
            return false;
        }
        sourceObject.addSubType(game, SubType.PLAINS, SubType.ISLAND, SubType.SWAMP, SubType.MOUNTAIN, SubType.FOREST);
        sourceObject.setIsAllNonbasicLandTypes(game, true);
        // subtypes apply in all zones ^
        // mana abilities apply to permanent
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return true;
        }
        // Optimization: Remove basic mana abilities since they are redundant with AnyColorManaAbility
        //               and keeping them will only produce too many combinations inside ManaOptions
        for (Ability basicManaAbility : basicManaAbilities) {
            if (permanent.getAbilities(game).containsRule(basicManaAbility)) {
                permanent.removeAbility(basicManaAbility, source.getSourceId(), game);
            }
        }
        // Add the {T}: Add one mana of any color ability
        // This is functionally equivalent to having five "{T}: Add {COLOR}" for each COLOR in {W}{U}{B}{R}{G}
        AnyColorManaAbility ability = new AnyColorManaAbility();
        if (!permanent.getAbilities(game).containsRule(ability)) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }
}
