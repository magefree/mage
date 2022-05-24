package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
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
public final class RecklessAmplimancer extends CardImpl {

    private static final DynamicValue sourcePower = new SourcePermanentPowerCount();

    public RecklessAmplimancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{G}: Double Reckless Amplimancer's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                sourcePower, SourcePermanentToughnessValue.getInstance(), Duration.EndOfTurn, true
        ).setText("double {this}'s power and toughness until end of turn"), new ManaCostsImpl<>("{4}{G}")));
    }

    private RecklessAmplimancer(final RecklessAmplimancer card) {
        super(card);
    }

    @Override
    public RecklessAmplimancer copy() {
        return new RecklessAmplimancer(this);
    }
}
