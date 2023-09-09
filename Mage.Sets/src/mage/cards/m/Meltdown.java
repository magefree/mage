
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class Meltdown extends CardImpl {

    public Meltdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");


        // Destroy each artifact with converted mana cost X or less.
        this.getSpellAbility().addEffect(new MeltdownEffect());
    }

    private Meltdown(final Meltdown card) {
        super(card);
    }

    @Override
    public Meltdown copy() {
        return new Meltdown(this);
    }
}

class MeltdownEffect extends OneShotEffect {
    
    MeltdownEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy each artifact with mana value X or less";
    }
    
    private MeltdownEffect(final MeltdownEffect effect) {
        super(effect);
    }
    
    @Override
    public MeltdownEffect copy() {
        return new MeltdownEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent != null && permanent.isArtifact(game) && permanent.getManaValue() <= source.getManaCostsToPay().getX()) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
