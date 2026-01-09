package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class FlaringCinder extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    public FlaringCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters and whenever you cast a spell with mana value 4 or greater, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        )));
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
            filter,
            true
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
