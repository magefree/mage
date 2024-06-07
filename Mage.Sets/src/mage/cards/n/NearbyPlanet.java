package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

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
}

class NearbyPlanetEffect extends ContinuousEffectImpl {

    NearbyPlanetEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Rangeling <i>(This card is every land type, including Plains, Island, Swamp, " +
                "Mountain, Forest, Desert, Gate, Lair, Locus, and all those Urza's ones.)</i>.";
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
        return true;
    }
}
