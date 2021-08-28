package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfTheLastBreath extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public KnightOfTheLastBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}, Sacrifice another nontoken creature: Create a 1/1 white and black spirit creature token with flying.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new WhiteBlackSpiritToken()), new GenericManaCost(3)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // Afterlife 3
        this.addAbility(new AfterlifeAbility(3));
    }

    private KnightOfTheLastBreath(final KnightOfTheLastBreath card) {
        super(card);
    }

    @Override
    public KnightOfTheLastBreath copy() {
        return new KnightOfTheLastBreath(this);
    }
}
