package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HeroToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DwarvenCastleGuard extends CardImpl {

    public DwarvenCastleGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature dies, create a 1/1 colorless Hero creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new HeroToken())));
    }

    private DwarvenCastleGuard(final DwarvenCastleGuard card) {
        super(card);
    }

    @Override
    public DwarvenCastleGuard copy() {
        return new DwarvenCastleGuard(this);
    }
}
