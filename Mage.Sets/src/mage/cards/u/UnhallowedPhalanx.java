package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnhallowedPhalanx extends CardImpl {

    public UnhallowedPhalanx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(13);

        // Unhallowed Phalanx enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private UnhallowedPhalanx(final UnhallowedPhalanx card) {
        super(card);
    }

    @Override
    public UnhallowedPhalanx copy() {
        return new UnhallowedPhalanx(this);
    }
}
