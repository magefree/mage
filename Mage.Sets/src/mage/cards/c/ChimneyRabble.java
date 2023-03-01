package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianGoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChimneyRabble extends CardImpl {

    public ChimneyRabble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Chimney Rabble enters the battlefield, create a 1/1 red Phyrexian Goblin creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianGoblinToken())));
    }

    private ChimneyRabble(final ChimneyRabble card) {
        super(card);
    }

    @Override
    public ChimneyRabble copy() {
        return new ChimneyRabble(this);
    }
}
