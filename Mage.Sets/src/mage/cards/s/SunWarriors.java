package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
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
public final class SunWarriors extends CardImpl {

    public SunWarriors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Firebending X, where X is the number of creatures you control.
        this.addAbility(new FirebendingAbility(CreaturesYouControlCount.PLURAL));

        // {5}: Create a 1/1 white Ally creature token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new AllyToken()), new GenericManaCost(5)));
    }

    private SunWarriors(final SunWarriors card) {
        super(card);
    }

    @Override
    public SunWarriors copy() {
        return new SunWarriors(this);
    }
}
