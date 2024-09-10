package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheShire extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public TheShire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // The Shire enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {1}{G}, {T}, Tap an untapped creature you control: Create a Food token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new FoodToken()), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)));
        this.addAbility(ability);
    }

    private TheShire(final TheShire card) {
        super(card);
    }

    @Override
    public TheShire copy() {
        return new TheShire(this);
    }
}
