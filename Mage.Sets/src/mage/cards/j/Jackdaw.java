package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author grimreap124
 */
public final class Jackdaw extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("artifact you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Jackdaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        // Whenever Jackdaw deals combat damage to a player, you may discard your hand. If you do, draw a card for each artifact you control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(xValue), new DiscardHandCost()), false));
        // Crew 3
        this.addAbility(new CrewAbility(3));

    }

    private Jackdaw(final Jackdaw card) {
        super(card);
    }

    @Override
    public Jackdaw copy() {
        return new Jackdaw(this);
    }
}
