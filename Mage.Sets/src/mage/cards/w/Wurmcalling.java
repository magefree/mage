
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.WurmCallingWurmToken;

/**
 *
 * @author LoneFox
 */
public final class Wurmcalling extends CardImpl {

    public Wurmcalling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Buyback {2}{G}
        this.addAbility(new BuybackAbility("{2}{G}"));
        // Create an X/X green Wurm creature token.
        this.getSpellAbility().addEffect(new WurmcallingEffect());
    }

    private Wurmcalling(final Wurmcalling card) {
        super(card);
    }

    @Override
    public Wurmcalling copy() {
        return new Wurmcalling(this);
    }
}

class WurmcallingEffect extends OneShotEffect {

    public WurmcallingEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Wurm creature token";
    }

    public WurmcallingEffect(WurmcallingEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = source.getManaCostsToPay().getX();
        WurmCallingWurmToken token = new WurmCallingWurmToken();
        token.setPower(count);
        token.setToughness(count);
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }

    @Override
    public WurmcallingEffect copy() {
        return new WurmcallingEffect(this);
    }
}
