
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LuminatePrimordial extends CardImpl {

    public LuminatePrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Luminate Primordial enters the battlefield, for each opponent, exile up to one target creature
        // that player controls and that player gains life equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LuminatePrimordialEffect(),false));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for(UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature from opponent " + opponent.getLogName());
                    filter.add(new ControllerIdPredicate(opponentId));
                    TargetCreaturePermanent target = new TargetCreaturePermanent(0,1, filter,false);
                    ability.addTarget(target);
                }
            }
        }
    }

    public LuminatePrimordial(final LuminatePrimordial card) {
        super(card);
    }

    @Override
    public LuminatePrimordial copy() {
        return new LuminatePrimordial(this);
    }
}

class LuminatePrimordialEffect extends OneShotEffect {

    public LuminatePrimordialEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each opponent, exile up to one target creature that player controls and that player gains life equal to its power";
    }

    public LuminatePrimordialEffect(final LuminatePrimordialEffect effect) {
        super(effect);
    }

    @Override
    public LuminatePrimordialEffect copy() {
        return new LuminatePrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target: source.getTargets()) {
            if (target instanceof TargetCreaturePermanent) {
                Permanent targetCreature = game.getPermanent(target.getFirstTarget());
                if (targetCreature != null && !targetCreature.isControlledBy(source.getControllerId())) {
                    int amountLife = targetCreature.getPower().getValue();
                    Player controller = game.getPlayer(targetCreature.getControllerId());
                    targetCreature.moveToExile(null, null, source.getSourceId(), game);
                    if (controller != null && amountLife != 0) {
                        controller.gainLife(amountLife, game, source);
                    }
                }
            }
        }
        return true;
    }
}
