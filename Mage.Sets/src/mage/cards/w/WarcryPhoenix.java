
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class WarcryPhoenix extends CardImpl {

    public WarcryPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you attack with three or more creatures, you may pay {2}{R}. If you do, return Warcry Phoenix from your graveyard to the battlefield tapped and attacking.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(Zone.GRAVEYARD,
                new DoIfCostPaid(new ReturnToBattlefieldUnderOwnerControlSourceEffect(true, true, -1),
                        new ManaCostsImpl<>("{2}{R}"))
                        .setText("you may pay {2}{R}. If you do, return {this} from your graveyard to the battlefield tapped and attacking"),
                3, StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private WarcryPhoenix(final WarcryPhoenix card) {
        super(card);
    }

    @Override
    public WarcryPhoenix copy() {
        return new WarcryPhoenix(this);
    }
}
