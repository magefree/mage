package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SetessanPetitioner extends CardImpl {

    public SetessanPetitioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Setessan Petitioner enters the battlefield, you gain life equal to your devotion to green.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainLifeEffect(DevotionCount.G).setText("you gain life equal to your devotion to green")
        ).addHint(DevotionCount.G.getHint()));
    }

    private SetessanPetitioner(final SetessanPetitioner card) {
        super(card);
    }

    @Override
    public SetessanPetitioner copy() {
        return new SetessanPetitioner(this);
    }
}
