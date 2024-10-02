package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrnOfGodfire extends CardImpl {

    public UrnOfGodfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new GenericManaCost(2)));

        // {6}, {T}, Sacrifice Urn of Godfire: Destroy target creature or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private UrnOfGodfire(final UrnOfGodfire card) {
        super(card);
    }

    @Override
    public UrnOfGodfire copy() {
        return new UrnOfGodfire(this);
    }
}
