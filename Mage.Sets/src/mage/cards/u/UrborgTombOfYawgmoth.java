package mage.cards.u;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.AddBasicLandTypeAllLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class UrborgTombOfYawgmoth extends CardImpl {

    public UrborgTombOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // Each land is a Swamp in addition to its other land types.
        ContinuousEffect effect = new AddBasicLandTypeAllLandsEffect(SubType.SWAMP);
        effect.getDependedToTypes().add(DependencyType.BecomeNonbasicLand);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private UrborgTombOfYawgmoth(final UrborgTombOfYawgmoth card) {
        super(card);
    }

    @Override
    public UrborgTombOfYawgmoth copy() {
        return new UrborgTombOfYawgmoth(this);
    }
}
