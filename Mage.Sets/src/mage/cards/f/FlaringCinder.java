package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FlaringCinder extends CardImpl {

    public FlaringCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters and whenever you cast a spell with mana value 4 or greater, you may discard a card. If you do, draw a card.
        this.addAbility(new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
            false,
            "When this creature enters and whenever you cast a spell with mana value 4 or greater, ",
            new EntersBattlefieldTriggeredAbility(null),
            new SpellCastControllerTriggeredAbility(null, StaticFilters.FILTER_SPELL_MV_4_OR_GREATER, true)
        ));
    }

    private FlaringCinder(final FlaringCinder card) {
        super(card);
    }

    @Override
    public FlaringCinder copy() {
        return new FlaringCinder(this);
    }
}
