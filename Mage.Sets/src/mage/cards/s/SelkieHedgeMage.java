
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SelkieHedgeMage extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.FOREST, "you control two or more Forests"),
            ComparisonType.MORE_THAN, 1
    );
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.ISLAND, "you control two or more Islands"),
            ComparisonType.MORE_THAN, 1
    );
    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SelkieHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Selkie Hedge-Mage enters the battlefield, if you control two or more Forests, you may gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3), true).withInterveningIf(condition));

        // When Selkie Hedge-Mage enters the battlefield, if you control two or more Islands, you may return target tapped creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true).withInterveningIf(condition2);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SelkieHedgeMage(final SelkieHedgeMage card) {
        super(card);
    }

    @Override
    public SelkieHedgeMage copy() {
        return new SelkieHedgeMage(this);
    }
}
