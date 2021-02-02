
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class ElvishPioneer extends CardImpl {

    public ElvishPioneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Elvish Pioneer enters the battlefield, you may put a basic land card from your hand onto the battlefield tapped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_BASIC_LAND_A, false, true), false));
    }

    private ElvishPioneer(final ElvishPioneer card) {
        super(card);
    }

    @Override
    public ElvishPioneer copy() {
        return new ElvishPioneer(this);
    }
}
