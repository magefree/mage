
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.BearToken;

/**
 *
 * @author LevelX2
 */
public final class GrizzlyFate extends CardImpl {

    public GrizzlyFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Create two 2/2 green Bear creature tokens.
        // Threshold - Create four 2/2 green Bear creature tokens instead if seven or more cards are in your graveyard.
        Effect effect = new ConditionalOneShotEffect(new CreateTokenEffect(new BearToken(), 4),
                                                     new CreateTokenEffect(new BearToken(), 2),
                                                     new CardsInControllerGraveyardCondition(7),
                                                     "Create two 2/2 green Bear creature tokens.<br/><br/><i>Threshold</i> &mdash; Create four 2/2 green Bear creature tokens instead if seven or more cards are in your graveyard.");
        this.getSpellAbility().addEffect(effect);

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{G}{G}")));
    }

    private GrizzlyFate(final GrizzlyFate card) {
        super(card);
    }

    @Override
    public GrizzlyFate copy() {
        return new GrizzlyFate(this);
    }
}
