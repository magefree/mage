package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverwingSquadron extends CardImpl {

    public SilverwingSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Silverwing Squadron's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance, Duration.EndOfGame)
        ).addHint(CreaturesYouControlHint.instance));

        // Whenever Silverwing Squadron attacks, create a number of 2/2 white Knight creature tokens with vigilance equal to the number of opponents you have.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new KnightToken(), OpponentsCount.instance).setText(
                        "create a number of 2/2 white Knight creature tokens with vigilance " +
                                "equal to the number of opponents you have"
                ), false
        ));
    }

    private SilverwingSquadron(final SilverwingSquadron card) {
        super(card);
    }

    @Override
    public SilverwingSquadron copy() {
        return new SilverwingSquadron(this);
    }
}
