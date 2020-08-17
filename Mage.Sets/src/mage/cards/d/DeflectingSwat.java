package mage.cards.d;

import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.hint.common.ControlACommanderHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeflectingSwat extends CardImpl {

    public DeflectingSwat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // If you control a commander, you may cast this spell without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(null, ControlACommanderCondition.instance)
                .addHint(ControlACommanderHint.instance)
        );

        // You may chose new targets for target spell or ability.
        this.getSpellAbility().addTarget(new TargetStackObject());
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect());
    }

    private DeflectingSwat(final DeflectingSwat card) {
        super(card);
    }

    @Override
    public DeflectingSwat copy() {
        return new DeflectingSwat(this);
    }
}
