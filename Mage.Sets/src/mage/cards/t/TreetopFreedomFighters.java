package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreetopFreedomFighters extends CardImpl {

    public TreetopFreedomFighters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When this creature enters, create a 1/1 white Ally creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AllyToken())));
    }

    private TreetopFreedomFighters(final TreetopFreedomFighters card) {
        super(card);
    }

    @Override
    public TreetopFreedomFighters copy() {
        return new TreetopFreedomFighters(this);
    }
}
