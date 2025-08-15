package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoyalFireSage extends CardImpl {

    public LoyalFireSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Firebending 1
        this.addAbility(new FirebendingAbility(1));

        // {5}: Create a 1/1 white Ally creature token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new AllyToken()), new GenericManaCost(5)));
    }

    private LoyalFireSage(final LoyalFireSage card) {
        super(card);
    }

    @Override
    public LoyalFireSage copy() {
        return new LoyalFireSage(this);
    }
}
