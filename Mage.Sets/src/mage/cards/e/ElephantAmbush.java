
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.ElephantToken;

/**
 *
 * @author LevelX2
 */
public final class ElephantAmbush extends CardImpl {

    public ElephantAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}{G}");


        // Create a 3/3 green Elephant creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()));
        // Flashback {6}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{G}{G}")));
    }

    private ElephantAmbush(final ElephantAmbush card) {
        super(card);
    }

    @Override
    public ElephantAmbush copy() {
        return new ElephantAmbush(this);
    }
}
