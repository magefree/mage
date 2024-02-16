
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class UrzasGuilt extends CardImpl {

    public UrzasGuilt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Each player draws two cards, then discards three cards, then loses 4 life.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(2));
        Effect effect = new DiscardEachPlayerEffect(3, false);
        effect.setText(", then discards three cards");
        this.getSpellAbility().addEffect(effect);
        effect = new LoseLifeAllPlayersEffect(4);
        effect.setText(", then loses 4 life");
        this.getSpellAbility().addEffect(effect);
    }

    private UrzasGuilt(final UrzasGuilt card) {
        super(card);
    }

    @Override
    public UrzasGuilt copy() {
        return new UrzasGuilt(this);
    }
}
