package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VraskasConquistador extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.VRASKA.getPredicate());
    }

    public VraskasConquistador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Vraska's Conquistador attacks or blocks, if you control a Vraska planeswalker, target opponent loses 2 life and you gain 2 life.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        TriggeredAbility ability = new AttacksOrBlocksTriggeredAbility(new LoseLifeTargetEffect(2), false);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        ability.addHint(new ConditionHint(condition, "You control a Vraska planeswalker"));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability, condition,
                "Whenever {this} attacks or blocks, if you control a Vraska planeswalker, target opponent loses 2 life and you gain 2 life."));
    }

    private VraskasConquistador(final VraskasConquistador card) {
        super(card);
    }

    @Override
    public VraskasConquistador copy() {
        return new VraskasConquistador(this);
    }
}
