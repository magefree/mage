package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BeastToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HerdBaloth extends CardImpl {

    public HerdBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever one or more +1/+1 counters are put on Herd Baloth, you may create a 4/4 green Beast creature token.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(
                new CreateTokenEffect(new BeastToken2()), true
        ));
    }

    private HerdBaloth(final HerdBaloth card) {
        super(card);
    }

    @Override
    public HerdBaloth copy() {
        return new HerdBaloth(this);
    }
}
