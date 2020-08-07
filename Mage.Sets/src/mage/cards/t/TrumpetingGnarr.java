package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BeastToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrumpetingGnarr extends CardImpl {

    public TrumpetingGnarr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mutate {3}{G/U}{G/U}
        this.addAbility(new MutateAbility(this, "{3}{G/U}{G/U}"));

        // Whenever this creature mutates, create a 3/3 green Beast creature token.
        this.addAbility(new MutatesSourceTriggeredAbility(new CreateTokenEffect(new BeastToken())));
    }

    private TrumpetingGnarr(final TrumpetingGnarr card) {
        super(card);
    }

    @Override
    public TrumpetingGnarr copy() {
        return new TrumpetingGnarr(this);
    }
}
