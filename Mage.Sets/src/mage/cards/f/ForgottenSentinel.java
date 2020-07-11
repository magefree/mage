package mage.cards.f;

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
public final class ForgottenSentinel extends CardImpl {

    public ForgottenSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Forgotten Sentinel enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private ForgottenSentinel(final ForgottenSentinel card) {
        super(card);
    }

    @Override
    public ForgottenSentinel copy() {
        return new ForgottenSentinel(this);
    }
}
