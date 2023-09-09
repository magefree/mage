
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class Exile extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public Exile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Exile target nonwhite attacking creature. 
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        // You gain life equal to its toughness.
        this.getSpellAbility().addEffect(new ExileEffect());
    }

    private Exile(final Exile card) {
        super(card);
    }

    @Override
    public Exile copy() {
        return new Exile(this);
    }
}

class ExileEffect extends OneShotEffect {

    public ExileEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to its toughness";
    }

    private ExileEffect(final ExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game, source);
            }
        }
        return false;
    }

    @Override
    public ExileEffect copy() {
        return new ExileEffect(this);
    }
}
