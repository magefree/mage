
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author stravant
 */
public final class AhnCropChampion extends CardImpl {
    public AhnCropChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may exert Ahn-Crop Champion as it attacks. When you do, untap all other creatures you control.
        addAbility(new ExertAbility(new BecomesExertSourceTriggeredAbility(new UntapAllControllerEffect(StaticFilters.FILTER_CONTROLLED_CREATURES, null, false))));
    }

    private AhnCropChampion(final AhnCropChampion card) {
        super(card);
    }

    @Override
    public AhnCropChampion copy() {
        return new AhnCropChampion(this);
    }
}
