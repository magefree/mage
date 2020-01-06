package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlazaOfHarmony extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.GATE, "Gate");

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public PlazaOfHarmony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // When Plaza of Harmony enters the battlefield, if you control two or more Gates, you gain 3 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)),
                condition, "When {this} enters the battlefield, " +
                "if you control two or more Gates, you gain 3 life."
        ).addHint(new ConditionHint(condition, "You control two or more Gates")));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any type a Gate you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU, false, filter2));
    }

    private PlazaOfHarmony(final PlazaOfHarmony card) {
        super(card);
    }

    @Override
    public PlazaOfHarmony copy() {
        return new PlazaOfHarmony(this);
    }
}
