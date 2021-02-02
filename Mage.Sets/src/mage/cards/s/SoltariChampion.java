
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author KholdFuzion
 */
public final class SoltariChampion extends CardImpl {

    public SoltariChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SOLTARI);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever Soltari Champion attacks, other creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, true), false));
    }

    private SoltariChampion(final SoltariChampion card) {
        super(card);
    }

    @Override
    public SoltariChampion copy() {
        return new SoltariChampion(this);
    }
}
