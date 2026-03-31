package mage.cards.a;

import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneOmens extends CardImpl {

    public ArcaneOmens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Converge -- Target player discards X cards, where X is the number of colors of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(ColorsOfManaSpentToCastCount.getInstance()));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
    }

    private ArcaneOmens(final ArcaneOmens card) {
        super(card);
    }

    @Override
    public ArcaneOmens copy() {
        return new ArcaneOmens(this);
    }
}
