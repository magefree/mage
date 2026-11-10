package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class SavvyProfiteer extends CardImpl {

    public SavvyProfiteer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.FERENGI);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {3}, {T}: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SavvyProfiteer(final SavvyProfiteer card) {
        super(card);
    }

    @Override
    public SavvyProfiteer copy() {
        return new SavvyProfiteer(this);
    }
}
