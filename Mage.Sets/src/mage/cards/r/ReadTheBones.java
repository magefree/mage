
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class ReadTheBones extends CardImpl {

    public ReadTheBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Scry 2, then draw two cards. You lose 2 life.
        this.getSpellAbility().addEffect(new ScryEffect(2));
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText("then draw two cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    public ReadTheBones(final ReadTheBones card) {
        super(card);
    }

    @Override
    public ReadTheBones copy() {
        return new ReadTheBones(this);
    }
}
