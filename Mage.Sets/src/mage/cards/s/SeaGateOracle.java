

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SeaGateOracle extends CardImpl {

    public SeaGateOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(StaticValue.get(2), false, StaticValue.get(1), new FilterCard(), Zone.LIBRARY, false, false), false));
    }


    private SeaGateOracle(final SeaGateOracle card) {
        super(card);
    }

    @Override
    public SeaGateOracle copy() {
        return new SeaGateOracle(this);
    }
}
