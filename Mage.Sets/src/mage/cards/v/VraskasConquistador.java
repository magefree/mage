package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VraskasConquistador extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.VRASKA, "you control a Vraska planeswalker")
    );
    private static final Hint hint = new ConditionHint(condition);

    public VraskasConquistador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Vraska's Conquistador attacks or blocks, if you control a Vraska planeswalker, target opponent loses 2 life and you gain 2 life.
        Ability ability = new AttacksOrBlocksTriggeredAbility(
                new LoseLifeTargetEffect(2), false
        ).withInterveningIf(condition);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(hint));
    }

    private VraskasConquistador(final VraskasConquistador card) {
        super(card);
    }

    @Override
    public VraskasConquistador copy() {
        return new VraskasConquistador(this);
    }
}
