
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class JaddiLifestrider extends CardImpl {

    public JaddiLifestrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(8);

        // When Jaddi Lifestrider enters the battlefield, you may tap any number of untapped creatures you control. You gain 2 life for each creature tapped this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JaddiLifestriderEffect(), true));
    }

    public JaddiLifestrider(final JaddiLifestrider card) {
        super(card);
    }

    @Override
    public JaddiLifestrider copy() {
        return new JaddiLifestrider(this);
    }
}

class JaddiLifestriderEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creatures you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public JaddiLifestriderEffect() {
        super(Outcome.GainLife);
        staticText = "you may tap any number of untapped creatures you control. You gain 2 life for each creature tapped this way";
    }

    public JaddiLifestriderEffect(JaddiLifestriderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int tappedAmount = 0;
        Player you = game.getPlayer(source.getControllerId());
        TargetCreaturePermanent target = new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), game)) {
            for (UUID creature : target.getTargets()) {
                if (creature != null) {
                    game.getPermanent(creature).tap(game);
                    tappedAmount++;
                }
            }
        }
        if (tappedAmount > 0) {
            you.gainLife(tappedAmount * 2, game, source);
            return true;
        }
        return false;
    }

    @Override
    public JaddiLifestriderEffect copy() {
        return new JaddiLifestriderEffect(this);
    }

}
