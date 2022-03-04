package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalkenrathCelebrants extends CardImpl {

    public FalkenrathCelebrants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Falkenrath Celebrants enters the battlefield, create two Blood tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken(), 2)));
    }

    private FalkenrathCelebrants(final FalkenrathCelebrants card) {
        super(card);
    }

    @Override
    public FalkenrathCelebrants copy() {
        return new FalkenrathCelebrants(this);
    }
}
