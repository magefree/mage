package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class GildedGoose extends CardImpl {
    
    public GildedGoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Gilded Goose enters the battlefield, create a Food token. (It’s an artifact with “{2}, {T}, Sacrifice this artifact: You gain 3 life.”)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken()), false));

        // {1}{G}, {T}: Create a Food token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new FoodToken()), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Sacrifice a Food: Add one mana of any color.
        ActivatedManaAbilityImpl ability1 = new AnyColorManaAbility(new TapSourceCost());
        ability1.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_FOOD)));
        this.addAbility(ability1);
    }

    private GildedGoose(final GildedGoose card) {
        super(card);
    }

    @Override
    public GildedGoose copy() {
        return new GildedGoose(this);
    }
}
