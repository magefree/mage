
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class BrilliantSpectrum extends CardImpl {

    public BrilliantSpectrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // <i>Converge</i> &mdash; Draw X cards, where X is the number of colors of mana spent to cast Brilliant Spectrum. Then discard two cards.
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        Effect effect = new DrawCardSourceControllerEffect(ColorsOfManaSpentToCastCount.getInstance());
        effect.setText("Draw X cards, where X is the number of colors of mana spent to cast {this}");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new DiscardControllerEffect(2));
    }

    public BrilliantSpectrum(final BrilliantSpectrum card) {
        super(card);
    }

    @Override
    public BrilliantSpectrum copy() {
        return new BrilliantSpectrum(this);
    }
}
