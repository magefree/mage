
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class ConsignOblivion extends SplitCard {

    public ConsignOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{U}", "{4}{B}", SpellAbilityType.SPLIT_AFTERMATH);

        // Return target nonland permanent to its owner's hand.
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Oblivion {4}{B}
        // Sorcery
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Target opponent discards two cards.
        getRightHalfCard().getSpellAbility().addEffect(new DiscardTargetEffect(2));
        getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    public ConsignOblivion(final ConsignOblivion card) {
        super(card);
    }

    @Override
    public ConsignOblivion copy() {
        return new ConsignOblivion(this);
    }
}
