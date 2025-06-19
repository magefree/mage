package mage.cards.p;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrickleFaeries extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 3, TargetController.ACTIVE);

    public PrickleFaeries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Prickle Faeries deals 2 damage to them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, TargetController.OPPONENT,
                new DamageTargetEffect(2, true, "them"), false
        ).withInterveningIf(condition));
    }

    private PrickleFaeries(final PrickleFaeries card) {
        super(card);
    }

    @Override
    public PrickleFaeries copy() {
        return new PrickleFaeries(this);
    }
}
