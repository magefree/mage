package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;

/**
 *
 * @author weirddan455
 * @author chesse20
 */
public final class SpeakeasyServerAlchemy extends CardImpl {

    public SpeakeasyServerAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Speakeasy Server enters the battlefield, you gain 1 life for each other creature you control.
        //this.addAbility(new EntersBattlefieldTriggeredAbility(new SpeakeasyServerAlchemyEffect()));

        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(CreaturesYouControlCount.instance).setText("you gain 1 life for each creature you control")));
    }

    private SpeakeasyServerAlchemy(final SpeakeasyServerAlchemy card) {
        super(card);
    }

    @Override
    public SpeakeasyServerAlchemy copy() {
        return new SpeakeasyServerAlchemy(this);
    }
}
