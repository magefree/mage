package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SpotFelisCatus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken legendary permanent");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public SpotFelisCatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Spot or another nontoken legendary permanent you control enters, create a Food token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new FoodToken()), filter, false, true
        ));

        // {4}, Sacrifice a Food: Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(4));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private SpotFelisCatus(final SpotFelisCatus card) {
        super(card);
    }

    @Override
    public SpotFelisCatus copy() {
        return new SpotFelisCatus(this);
    }
}
