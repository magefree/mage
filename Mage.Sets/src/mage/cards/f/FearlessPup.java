package mage.cards.f;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearlessPup extends CardImpl {

    public FearlessPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Boast — {2}{R}: Fearless Pup gets +2/+0 until end of turn.
        this.addAbility(new BoastAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), "{2}{R}"));
    }

    private FearlessPup(final FearlessPup card) {
        super(card);
    }

    @Override
    public FearlessPup copy() {
        return new FearlessPup(this);
    }
}
