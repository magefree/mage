package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoatToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiscordantPiper extends CardImpl {

    public DiscordantPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Discordant Piper dies, create a 0/1 white Goat creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new GoatToken())));
    }

    private DiscordantPiper(final DiscordantPiper card) {
        super(card);
    }

    @Override
    public DiscordantPiper copy() {
        return new DiscordantPiper(this);
    }
}
