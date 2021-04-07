package mage.cards.f;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.OpponentsCantCastChosenUntilNextTurnEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class FailureComply extends SplitCard {

    public FailureComply(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{U}", "{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Failure
        // Return target spell to it's owner's hand
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // to
        // Comply
        // Choose a card name.  Until your next turn, your opponents can't cast spells with the chosen name
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        Effect effect = new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL);
        effect.setText("Choose a card name");
        getRightHalfCard().getSpellAbility().addEffect(effect);
        getRightHalfCard().getSpellAbility().addEffect(new OpponentsCantCastChosenUntilNextTurnEffect());
    }

    private FailureComply(final FailureComply card) {
        super(card);
    }

    @Override
    public FailureComply copy() {
        return new FailureComply(this);
    }
}
