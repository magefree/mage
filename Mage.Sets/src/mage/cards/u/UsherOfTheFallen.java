package mage.cards.u;

import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UsherOfTheFallen extends CardImpl {

    public UsherOfTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Boast — {1}{W}: Create a 1/1 white Human Warrior creature token.
        this.addAbility(new BoastAbility(new CreateTokenEffect(new HumanWarriorToken()), "{1}{W}"));
    }

    private UsherOfTheFallen(final UsherOfTheFallen card) {
        super(card);
    }

    @Override
    public UsherOfTheFallen copy() {
        return new UsherOfTheFallen(this);
    }
}
