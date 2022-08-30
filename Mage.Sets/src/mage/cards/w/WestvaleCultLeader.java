package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.HumanClericToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WestvaleCultLeader extends CardImpl {

    public WestvaleCultLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance, Duration.EndOfGame))
                .addHint(CreaturesYouControlHint.instance));

        // At the beginning of your end step, create a 1/1 white and black Human Cleric creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new HumanClericToken()), TargetController.YOU, false));
    }

    private WestvaleCultLeader(final WestvaleCultLeader card) {
        super(card);
    }

    @Override
    public WestvaleCultLeader copy() {
        return new WestvaleCultLeader(this);
    }
}
