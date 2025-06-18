package mage.cards.w;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WanderingChampion extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control a blue or red permanent");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.RED)
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public WanderingChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Wandering Champion deals combat damage to a player, if you control a blue or red permanent, you may discard a card. If you do, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ).withInterveningIf(condition));
    }

    private WanderingChampion(final WanderingChampion card) {
        super(card);
    }

    @Override
    public WanderingChampion copy() {
        return new WanderingChampion(this);
    }
}
