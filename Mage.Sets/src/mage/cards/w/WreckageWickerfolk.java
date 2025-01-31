package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WreckageWickerfolk extends CardImpl {

    public WreckageWickerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));
    }

    private WreckageWickerfolk(final WreckageWickerfolk card) {
        super(card);
    }

    @Override
    public WreckageWickerfolk copy() {
        return new WreckageWickerfolk(this);
    }
}
