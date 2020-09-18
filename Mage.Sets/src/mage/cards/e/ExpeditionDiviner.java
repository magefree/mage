package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.UUID;
import mage.abilities.common.DiesSourceTriggeredAbility;

/**
 * @author TheElk801
 */
public final class ExpeditionDiviner extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WIZARD);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public ExpeditionDiviner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as you control another Wizard, Expedition Diviner has "When this creature dies, draw a card."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1))),
                condition, "As long as you control another Wizard, {this} has \"When this creature dies, draw a card.\""
        )));
    }

    private ExpeditionDiviner(final ExpeditionDiviner card) {
        super(card);
    }

    @Override
    public ExpeditionDiviner copy() {
        return new ExpeditionDiviner(this);
    }
}
