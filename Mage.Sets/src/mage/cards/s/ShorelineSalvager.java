
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ShorelineSalvager extends CardImpl {

    private static final String rule = "Whenever Shoreline Salvager deals combat damage to a player, if you control an Island, you may draw a card.";
    private static final FilterPermanent filter = new FilterPermanent("Island");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.ISLAND.getPredicate());
    }

    public ShorelineSalvager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SURRAKAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Shoreline Salvager deals combat damage to a player, if you control an Island, you may draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true), new PermanentsOnTheBattlefieldCondition(filter), rule));
    }

    private ShorelineSalvager(final ShorelineSalvager card) {
        super(card);
    }

    @Override
    public ShorelineSalvager copy() {
        return new ShorelineSalvager(this);
    }
}
