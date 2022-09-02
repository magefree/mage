package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SeraphOfTheMasses extends CardImpl {

    public SeraphOfTheMasses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Seraph of the Masses's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance, Duration.EndOfGame))
                .addHint(CreaturesYouControlHint.instance));
    }

    private SeraphOfTheMasses(final SeraphOfTheMasses card) {
        super(card);
    }

    @Override
    public SeraphOfTheMasses copy() {
        return new SeraphOfTheMasses(this);
    }
}
