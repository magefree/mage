package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroodWeaver extends CardImpl {

    public BroodWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Brood Weaver dies, create a 1/2 green Spider creature token with reach.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SpiderToken())));
    }

    private BroodWeaver(final BroodWeaver card) {
        super(card);
    }

    @Override
    public BroodWeaver copy() {
        return new BroodWeaver(this);
    }
}
