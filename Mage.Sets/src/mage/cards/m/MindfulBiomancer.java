package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindfulBiomancer extends CardImpl {

    public MindfulBiomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));

        // {2}{G}: This creature gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private MindfulBiomancer(final MindfulBiomancer card) {
        super(card);
    }

    @Override
    public MindfulBiomancer copy() {
        return new MindfulBiomancer(this);
    }
}
