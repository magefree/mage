package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ShorelineSalvager extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.ISLAND, "you control an Island")
    );

    public ShorelineSalvager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SURRAKAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Shoreline Salvager deals combat damage to a player, if you control an Island, you may draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true
        ).withInterveningIf(condition));
    }

    private ShorelineSalvager(final ShorelineSalvager card) {
        super(card);
    }

    @Override
    public ShorelineSalvager copy() {
        return new ShorelineSalvager(this);
    }
}
