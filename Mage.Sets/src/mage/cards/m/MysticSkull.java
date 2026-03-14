package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticSkull extends TransformingDoubleFacedCard {

    public MysticSkull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "Mystic Monstrosity",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.CONSTRUCT}, ""
        );

        // Mystic Skull
        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // {5}, {T}: Transform Mystic Skull.
        ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Mystic Monstrosity
        this.getRightHalfCard().setPT(5, 6);

        // Lands you control have "{T}: Add one mana of any color."
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS, false
        )));
    }

    private MysticSkull(final MysticSkull card) {
        super(card);
    }

    @Override
    public MysticSkull copy() {
        return new MysticSkull(this);
    }
}
