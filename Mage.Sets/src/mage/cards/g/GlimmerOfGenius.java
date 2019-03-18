
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class GlimmerOfGenius extends CardImpl {

    public GlimmerOfGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Scry 2, then draw two card. You get {E}{E}.
        this.getSpellAbility().addEffect(new ScryEffect(2));
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText(", then draw two card");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
    }

    public GlimmerOfGenius(final GlimmerOfGenius card) {
        super(card);
    }

    @Override
    public GlimmerOfGenius copy() {
        return new GlimmerOfGenius(this);
    }
}
