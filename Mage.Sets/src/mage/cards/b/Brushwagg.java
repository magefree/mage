package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class Brushwagg extends CardImpl {

    public Brushwagg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.BRUSHWAGG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Brushwagg blocks or becomes blocked, it gets -2/+2 until end of turn.
        this.addAbility(new BlocksOrBlockedSourceTriggeredAbility(new BoostSourceEffect(-2, 2, Duration.EndOfTurn, "it")));
    }

    private Brushwagg(final Brushwagg card) {
        super(card);
    }

    @Override
    public Brushwagg copy() {
        return new Brushwagg(this);
    }
}
