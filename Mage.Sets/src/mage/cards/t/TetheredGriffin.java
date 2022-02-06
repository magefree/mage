
package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class TetheredGriffin extends CardImpl {

    public TetheredGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you control no enchantments, sacrifice Tethered Griffin.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                StaticFilters.FILTER_PERMANENT_ENCHANTMENT, ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private TetheredGriffin(final TetheredGriffin card) {
        super(card);
    }

    @Override
    public TetheredGriffin copy() {
        return new TetheredGriffin(this);
    }
}
