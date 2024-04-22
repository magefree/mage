package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedcapThief extends CardImpl {

    public RedcapThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Redcap Thief enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private RedcapThief(final RedcapThief card) {
        super(card);
    }

    @Override
    public RedcapThief copy() {
        return new RedcapThief(this);
    }
}
