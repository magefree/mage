package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightedMyr extends CardImpl {

    public KnightedMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MYR);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{W}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{2}{W}"));

        // Whenever one or more +1/+1 counters are put on Knighted Myr, it gains double strike until end of turn.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new GainAbilitySourceEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains double strike until end of turn")));
    }

    private KnightedMyr(final KnightedMyr card) {
        super(card);
    }

    @Override
    public KnightedMyr copy() {
        return new KnightedMyr(this);
    }
}
