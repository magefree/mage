package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.LordOfThePitToken;
import mage.game.permanent.token.ZombieToken;
import mage.Mana;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ThomilTheDestroyer extends CardImpl {

    public ThomilTheDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.THOMIL);
        this.setStartingLoyalty(4);

        // +2: Create a 2/2 black Zombie creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ZombieToken()), 2));

        // 0: You may sacrifice a creature. If you do, add {B}{B}{B}.
        this.addAbility(new LoyaltyAbility(
            new DoIfCostPaid(
                new BasicManaEffect(Mana.BlackMana(3)),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)),
            0
        ));

        // -5: Create a Lord of the Pit token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new LordOfThePitToken()), -5));

        // Thomil, the Destroyer can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private ThomilTheDestroyer(final ThomilTheDestroyer card) {
        super(card);
    }

    @Override
    public ThomilTheDestroyer copy() {
        return new ThomilTheDestroyer(this);
    }
}
