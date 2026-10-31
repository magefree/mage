package mage.cards.d;

import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author muz
 */
public final class DeepSpaceScan extends CardImpl {

    public DeepSpaceScan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Strange new worlds -- Draw X cards, where X is the number of differently named lands you control. You have no maximum hand size until your next turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(StrangeNewWorldsValue.instance));
        this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(
            Integer.MAX_VALUE, Duration.UntilYourNextTurn, HandSizeModification.SET
        ).setText("you have no maximum hand size until your next turn"));
        this.getSpellAbility().setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS).addHint(StrangeNewWorldsHint.instance);
    }

    private DeepSpaceScan(final DeepSpaceScan card) {
        super(card);
    }

    @Override
    public DeepSpaceScan copy() {
        return new DeepSpaceScan(this);
    }
}
