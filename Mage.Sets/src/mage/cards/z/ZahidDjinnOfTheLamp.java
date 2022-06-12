
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ZahidDjinnOfTheLamp extends CardImpl {

    public ZahidDjinnOfTheLamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // You may pay {3}{U} and tap an untapped artifact you control rather than pay this spell's mana cost.
        AlternativeCostSourceAbility alternativeCostSourceAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{3}{U}"), null,
                "You may pay {3}{U} and tap an untapped artifact you control rather than pay this spell's mana cost.");
        FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("untapped artifact you control");
        filter.add(TappedPredicate.UNTAPPED);
        alternativeCostSourceAbility.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(alternativeCostSourceAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

    }

    private ZahidDjinnOfTheLamp(final ZahidDjinnOfTheLamp card) {
        super(card);
    }

    @Override
    public ZahidDjinnOfTheLamp copy() {
        return new ZahidDjinnOfTheLamp(this);
    }
}
