
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class AcademyRaider extends CardImpl {

    public AcademyRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());

        // Whenever Academy Raider deals combat damage to a player, you may discard a card. If you do, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), false));

    }

    private AcademyRaider(final AcademyRaider card) {
        super(card);
    }

    @Override
    public AcademyRaider copy() {
        return new AcademyRaider(this);
    }
}
