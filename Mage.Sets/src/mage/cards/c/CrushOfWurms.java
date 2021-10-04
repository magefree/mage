
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.WurmToken;

/**
 * @author JRHerlehy
 */
public final class CrushOfWurms extends CardImpl {

    public CrushOfWurms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}{G}{G}");


        // Put three 6/6 green Wurm creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WurmToken(), 3));
        // Flashback {9}{G}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{9}{G}{G}{G}")));
    }

    private CrushOfWurms(final CrushOfWurms card) {
        super(card);
    }

    @Override
    public CrushOfWurms copy() {
        return new CrushOfWurms(this);
    }
}
