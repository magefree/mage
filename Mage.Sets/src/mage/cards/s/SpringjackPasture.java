package mage.cards.s;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.GoatToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SpringjackPasture extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Goats");

    static {
        filter.add(SubType.GOAT.getPredicate());
    }

    public SpringjackPasture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {tap}: Create a 0/1 white Goat creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoatToken()), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Sacrifice X Goats: Add X mana of any one color. You gain X life.
        ability = new DynamicManaAbility(
                new Mana(0, 0, 0, 0, 0, 0, 1, 0),
                GetXValue.instance,
                new TapSourceCost(),
                "Add X mana of any one color",
                true);
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addEffect(new GainLifeEffect(GetXValue.instance));
        this.addAbility(ability);

    }

    private SpringjackPasture(final SpringjackPasture card) {
        super(card);
    }

    @Override
    public SpringjackPasture copy() {
        return new SpringjackPasture(this);
    }
}
