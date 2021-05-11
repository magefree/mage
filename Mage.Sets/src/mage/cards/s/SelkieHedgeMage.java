
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

/**
 * @author jeffwadsworth
 */
public final class SelkieHedgeMage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forests");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("Islands");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter2.add(SubType.ISLAND.getPredicate());
        filter3.add(TappedPredicate.TAPPED);
    }

    private static final String rule1 = "When {this} enters the battlefield, if you control two or more Forests, you may gain 3 life.";
    private static final String rule2 = "When {this} enters the battlefield, if you control two or more Islands, you may return target tapped creature to its owner's hand.";

    public SelkieHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Selkie Hedge-Mage enters the battlefield, if you control two or more Forests, you may gain 3 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule1);
        this.addAbility(ability);

        // When Selkie Hedge-Mage enters the battlefield, if you control two or more Islands, you may return target tapped creature to its owner's hand.
        Ability ability2 = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true), new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1), rule2);
        ability2.addTarget(new TargetPermanent(filter3));
        this.addAbility(ability2);

    }

    private SelkieHedgeMage(final SelkieHedgeMage card) {
        super(card);
    }

    @Override
    public SelkieHedgeMage copy() {
        return new SelkieHedgeMage(this);
    }
}
