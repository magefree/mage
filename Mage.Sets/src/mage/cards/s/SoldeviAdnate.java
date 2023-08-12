
package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.HighestCMCOfPermanentValue;
import mage.abilities.dynamicvalue.common.SacrificeCostManaValue;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class SoldeviAdnate extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("black or artifact creature");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), CardType.ARTIFACT.getPredicate()));
    }

    public SoldeviAdnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice a black or artifact creature: Add an amount of {B} equal to the sacrificed creature's converted mana cost.
        Ability ability = new DynamicManaAbility(Mana.BlackMana(1), SacrificeCostManaValue.CREATURE, new TapSourceCost(),
                "add an amount of {B} equal to the sacrificed creature's mana value", false,
                new HighestCMCOfPermanentValue(filter, true));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private SoldeviAdnate(final SoldeviAdnate card) {
        super(card);
    }

    @Override
    public SoldeviAdnate copy() {
        return new SoldeviAdnate(this);
    }
}
