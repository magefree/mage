
package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class UrborgTombOfYawgmoth extends CardImpl {

    public UrborgTombOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        addSuperType(SuperType.LEGENDARY);

        // Each land is a Swamp in addition to its other land types.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new BlackManaAbility(), Duration.WhileOnBattlefield, new FilterLandPermanent(),
                "Each land is a Swamp in addition to its other land types"));
        ability.addEffect(new AddCardSubtypeAllEffect(new FilterLandPermanent(), SubType.SWAMP, DependencyType.BecomeSwamp));
        this.addAbility(ability);

    }

    public UrborgTombOfYawgmoth(final UrborgTombOfYawgmoth card) {
        super(card);
    }

    @Override
    public UrborgTombOfYawgmoth copy() {
        return new UrborgTombOfYawgmoth(this);
    }
}
