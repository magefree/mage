package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class Vexis extends CardImpl {
    public Vexis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.addSubType(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        //{W}: Monstrosity 2
        this.addAbility(new MonstrosityAbility("{W}", 2));

        //Whenever a +1/+1 counter is put on Vexis, it gains vigilance until end of turn.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn), false, CounterType.P1P1));
    }

    public Vexis(final Vexis card) {
        super(card);
    }

    @Override
    public Vexis copy() {
        return new Vexis(this);
    }
}
