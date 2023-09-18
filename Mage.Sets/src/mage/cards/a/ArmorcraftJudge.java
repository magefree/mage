
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ArmorcraftJudge extends CardImpl {

    public ArmorcraftJudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Armorcraft Judge enters the battlefield, draw a card for each creature you control with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1))
        ));
    }

    private ArmorcraftJudge(final ArmorcraftJudge card) {
        super(card);
    }

    @Override
    public ArmorcraftJudge copy() {
        return new ArmorcraftJudge(this);
    }
}
