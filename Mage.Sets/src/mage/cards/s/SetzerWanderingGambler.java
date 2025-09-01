package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.WonCoinFlipControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TheBlackjackToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SetzerWanderingGambler extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VEHICLE);

    public SetzerWanderingGambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Setzer enters, create The Blackjack, a legendary 3/3 colorless Vehicle artifact token with flying and crew 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TheBlackjackToken())));

        // Whenever a Vehicle you control deals combat damage to a player, flip a coin.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new FlipCoinEffect(), filter, false, SetTargetPointer.NONE, true
        ));

        // Whenever you win a coin flip, create two tapped Treasure tokens.
        this.addAbility(new WonCoinFlipControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 2, true)
        ));
    }

    private SetzerWanderingGambler(final SetzerWanderingGambler card) {
        super(card);
    }

    @Override
    public SetzerWanderingGambler copy() {
        return new SetzerWanderingGambler(this);
    }
}
