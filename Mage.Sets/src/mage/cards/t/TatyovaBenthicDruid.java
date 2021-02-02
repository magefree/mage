
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author L_J
 */
public final class TatyovaBenthicDruid extends CardImpl {

    public TatyovaBenthicDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK, SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield under your control, you gain 1 life and draw a card.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), StaticFilters.FILTER_LAND_A);
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and draw a card"));
        this.addAbility(ability);
    }

    private TatyovaBenthicDruid(final TatyovaBenthicDruid card) {
        super(card);
    }

    @Override
    public TatyovaBenthicDruid copy() {
        return new TatyovaBenthicDruid(this);
    }
}
