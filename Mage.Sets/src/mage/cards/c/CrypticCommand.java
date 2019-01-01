
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author jonubuu
 */
public final class CrypticCommand extends CardImpl {

    public CrypticCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}{U}");


        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Counter target spell;
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        // or return target permanent to its owner's hand;
        Mode mode = new Mode();
        mode.addEffect(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetPermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // or tap all creatures your opponents control;
        mode = new Mode();
        mode.addEffect(new CrypticCommandEffect());
        this.getSpellAbility().getModes().addMode(mode);
        // or draw a card.
        mode = new Mode();
        mode.addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
    }

    public CrypticCommand(final CrypticCommand card) {
        super(card);
    }

    @Override
    public CrypticCommand copy() {
        return new CrypticCommand(this);
    }
}

class CrypticCommandEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
      filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public CrypticCommandEffect() {
        super(Outcome.Tap);
        staticText = "tap all creatures your opponents control";
    }

    public CrypticCommandEffect(final CrypticCommandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, player.getId(), source.getSourceId(), game)) {
            creature.tap(game);
        }
        return true;
    }

    @Override
    public CrypticCommandEffect copy() {
        return new CrypticCommandEffect(this);
    }
}
