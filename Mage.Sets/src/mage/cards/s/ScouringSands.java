
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class ScouringSands extends CardImpl {

    public ScouringSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Scouring Sands deals 1 damage to each creature your opponents control. Scry 1.
        this.getSpellAbility().addEffect(new ScouringSandsDamageEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1));
        
    }

    private ScouringSands(final ScouringSands card) {
        super(card);
    }

    @Override
    public ScouringSands copy() {
        return new ScouringSands(this);
    }
}

class ScouringSandsDamageEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");
    
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ScouringSandsDamageEffect() {
        super(Outcome.GainLife);
        staticText = "{this} deals 1 damage to each creature your opponents control";
    }

    private ScouringSandsDamageEffect(final ScouringSandsDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (creature != null) {
                creature.damage(1, source.getSourceId(), source, game, false, false);
            }
        }
        return true;
    }

    @Override
    public ScouringSandsDamageEffect copy() {
        return new ScouringSandsDamageEffect(this);
    }

}
