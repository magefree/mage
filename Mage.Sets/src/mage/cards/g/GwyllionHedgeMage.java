package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.KithkinSoldierToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GwyllionHedgeMage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("Swamps");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter2.add(SubType.SWAMP.getPredicate());
    }

    private static final String rule1 = "When {this} enters the battlefield, if you control two or more Plains, you may create a 1/1 white Kithkin Soldier creature token.";
    private static final String rule2 = "When {this} enters the battlefield, if you control two or more Swamps, you may put a -1/-1 counter on target creature.";

    public GwyllionHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/B}");
        this.subtype.add(SubType.HAG);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gwyllion Hedge-Mage enters the battlefield, if you control two or more Plains, you may create a 1/1 white Kithkin Soldier creature token.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinSoldierToken()), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule1);
        this.addAbility(ability);

        // When Gwyllion Hedge-Mage enters the battlefield, if you control two or more Swamps, you may put a -1/-1 counter on target creature.
        Ability ability2 = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true),
                new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1),
                rule2);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

    }

    private GwyllionHedgeMage(final GwyllionHedgeMage card) {
        super(card);
    }

    @Override
    public GwyllionHedgeMage copy() {
        return new GwyllionHedgeMage(this);
    }
}
