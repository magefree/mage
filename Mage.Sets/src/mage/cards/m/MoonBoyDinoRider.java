package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MoonBoyDinoRider extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dinosaur spells");
    private static final FilterPermanent filter2
        = new FilterControlledPermanent(SubType.DINOSAUR, "you control a Dinosaur");

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter2, true);
    private static final Hint hint = new ConditionHint(condition, "You control a Dinosaur");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public MoonBoyDinoRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Dinosaur spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever Moon-Boy attacks while you control a Dinosaur, Moon-Boy gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
            new BoostSourceEffect(1, 1, Duration.EndOfTurn)
        ).withRuleTextReplacement(false).withTriggerCondition(condition).addHint(hint));
    }

    private MoonBoyDinoRider(final MoonBoyDinoRider card) {
        super(card);
    }

    @Override
    public MoonBoyDinoRider copy() {
        return new MoonBoyDinoRider(this);
    }
}
