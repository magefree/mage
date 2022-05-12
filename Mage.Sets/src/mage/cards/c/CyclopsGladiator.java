

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CyclopsGladiator extends CardImpl {

    public CyclopsGladiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}{R}");
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Cyclops Gladiator attacks, you may have it deal damage equal to its power to target creature defending player controls. 
        // If you do, that creature deals damage equal to its power to Cyclops Gladiator.
        Ability ability = new AttacksTriggeredAbility(new CyclopsGladiatorEffect(), true);
        this.addAbility(ability);
    }

    private CyclopsGladiator(final CyclopsGladiator card) {
        super(card);
    }

    @Override
    public CyclopsGladiator copy() {
        return new CyclopsGladiator(this);
    }

}

class CyclopsGladiatorEffect extends OneShotEffect {

    public CyclopsGladiatorEffect() {
        super(Outcome.Damage);
        staticText = "you may have it deal damage equal to its power to target creature defending player controls. If you do, that creature deals damage equal to its power to {this}";
    }

    public CyclopsGladiatorEffect(final CyclopsGladiatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        if (defenderId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            filter.add(new ControllerIdPredicate(defenderId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            Player player = game.getPlayer(source.getControllerId());
            if (target.canChoose(source.getControllerId(), source, game)) {
                if (player != null && player.chooseTarget(Outcome.Detriment, target, source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    Permanent cyclops = game.getPermanent(source.getSourceId());
                    if (permanent != null && cyclops != null) {
                        permanent.damage(cyclops.getPower().getValue(), cyclops.getId(), source, game, false, true);
                        cyclops.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CyclopsGladiatorEffect copy() {
        return new CyclopsGladiatorEffect(this);
    }

}
