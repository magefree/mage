
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class AngelOfRenewal extends CardImpl {

    public AngelOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Angel of Renewal enters the battlefield, you gain 1 life for each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)
        )));
    }

    public AngelOfRenewal(final AngelOfRenewal card) {
        super(card);
    }

    @Override
    public AngelOfRenewal copy() {
        return new AngelOfRenewal(this);
    }
}
