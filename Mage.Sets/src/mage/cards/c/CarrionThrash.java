
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class CarrionThrash extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("another target creature card from your graveyard");

    static {
        filter.add(new AnotherCardPredicate());
    }

    public CarrionThrash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Carrion Thrash dies, you may pay {2}. If you do, return another target creature card from your graveyard to your hand.
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(2)), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private CarrionThrash(final CarrionThrash card) {
        super(card);
    }

    @Override
    public CarrionThrash copy() {
        return new CarrionThrash(this);
    }
}
