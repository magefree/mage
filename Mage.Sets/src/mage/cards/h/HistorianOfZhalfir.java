package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HistorianOfZhalfir extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent(SubType.TEFERI);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public HistorianOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Historian of Zhalfir attacks, if you control a Teferi planeswalker, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1), false),
                condition, "Whenever {this} attacks, if you control a Teferi planeswalker, draw a card."
        ));
    }

    private HistorianOfZhalfir(final HistorianOfZhalfir card) {
        super(card);
    }

    @Override
    public HistorianOfZhalfir copy() {
        return new HistorianOfZhalfir(this);
    }
}
