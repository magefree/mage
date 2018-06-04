
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GraveStrength extends CardImpl {

    public GraveStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Choose target creature. Put the top three cards of your library into your graveyard, then put a +1/+1 counter on that creature for each creature card in your graveyard.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new PutTopCardOfLibraryIntoGraveControllerEffect(3);
        effect.setText("Choose target creature. Put the top three cards of your library into your graveyard");
        this.getSpellAbility().addEffect(effect);
        effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), new CardsInControllerGraveyardCount(new FilterCreatureCard()));
        effect.setText(", then put a +1/+1 counter on that creature for each creature card in your graveyard");
        this.getSpellAbility().addEffect(effect);

    }

    public GraveStrength(final GraveStrength card) {
        super(card);
    }

    @Override
    public GraveStrength copy() {
        return new GraveStrength(this);
    }
}
