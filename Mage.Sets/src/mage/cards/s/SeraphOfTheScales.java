package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.AfterlifeAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeraphOfTheScales extends CardImpl {

    public SeraphOfTheScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {W}: Seraph of the Scales gains vigilance until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        VigilanceAbility.getInstance(),
                        Duration.EndOfTurn
                ), new ManaCostsImpl<>("{W}")
        ));

        // {B}: Seraph of the Scales gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        DeathtouchAbility.getInstance(),
                        Duration.EndOfTurn
                ), new ManaCostsImpl<>("{B}")
        ));

        // Afterlife 2
        this.addAbility(new AfterlifeAbility(2));
    }

    private SeraphOfTheScales(final SeraphOfTheScales card) {
        super(card);
    }

    @Override
    public SeraphOfTheScales copy() {
        return new SeraphOfTheScales(this);
    }
}
