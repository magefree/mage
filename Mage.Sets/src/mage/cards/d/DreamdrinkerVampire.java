package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DreamdrinkerVampire extends CardImpl {

    public DreamdrinkerVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {1}{B}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{1}{B}"));

        // Whenever one or more +1/+1 counters are put on Dreamdrinker Vampire, it gains menace until end of turn.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(
                new GainAbilitySourceEffect(new MenaceAbility(false), Duration.EndOfTurn)
        ));
    }

    private DreamdrinkerVampire(final DreamdrinkerVampire card) {
        super(card);
    }

    @Override
    public DreamdrinkerVampire copy() {
        return new DreamdrinkerVampire(this);
    }
}
