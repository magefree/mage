
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class GazeOfGranite extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each nonland permanent with mana value X or less");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GazeOfGranite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}{G}");


        // Destroy each nonland permanent with converted mana cost X or less.
         this.getSpellAbility().addEffect(new GazeOfGraniteEffect());
    }

    private GazeOfGranite(final GazeOfGranite card) {
        super(card);
    }

    @Override
    public GazeOfGranite copy() {
        return new GazeOfGranite(this);
    }
}

class GazeOfGraniteEffect extends OneShotEffect {

    public GazeOfGraniteEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with mana value X or less";
    }

    private GazeOfGraniteEffect(final GazeOfGraniteEffect effect) {
        super(effect);
    }

    @Override
    public GazeOfGraniteEffect copy() {
        return new GazeOfGraniteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (!permanent.isLand(game) && permanent.getManaValue() <= source.getManaCostsToPay().getX()) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }

}
