package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JaddiLifestrider extends CardImpl {

    public JaddiLifestrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(8);

        // When Jaddi Lifestrider enters the battlefield, you may tap any number of untapped creatures you control. You gain 2 life for each creature tapped this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JaddiLifestriderEffect(), true));
    }

    private JaddiLifestrider(final JaddiLifestrider card) {
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
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TappedPredicate.UNTAPPED);
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
        if (target.canChoose(source.getControllerId(), source, game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), source, game)) {
            for (UUID creatureId : target.getTargets()) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    creature.tap(source, game);
                    tappedAmount++;
                }
            }
        }
        if (tappedAmount > 0 && you != null) {
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
