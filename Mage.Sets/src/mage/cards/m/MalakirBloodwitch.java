package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MalakirBloodwitch extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.VAMPIRE));
    private static final Hint hint = new ValueHint("Vampires you control", xValue);

    public MalakirBloodwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // When Malakir Bloodwitch enters the battlefield, each opponent loses life equal to the number of Vampires you control. You gain life equal to the life lost this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LoseLifeOpponentsYouGainLifeLostEffect(xValue,
                        "life equal to the number of Vampires you control"), false
                ).addHint(hint)
        );
    }

    private MalakirBloodwitch(final MalakirBloodwitch card) {
        super(card);
    }

    @Override
    public MalakirBloodwitch copy() {
        return new MalakirBloodwitch(this);
    }
}
