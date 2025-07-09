package mage.cards.k;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Kezzerdrix extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterOpponentsCreaturePermanent("your opponents control no creatures"), ComparisonType.EQUAL_TO, 0
    );

    public Kezzerdrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, if your opponents control no creatures, Kezzerdrix deals 4 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(4)).withInterveningIf(condition));
    }

    private Kezzerdrix(final Kezzerdrix card) {
        super(card);
    }

    @Override
    public Kezzerdrix copy() {
        return new Kezzerdrix(this);
    }
}
