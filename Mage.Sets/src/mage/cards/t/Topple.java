package mage.cards.t;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Topple extends CardImpl {

    public Topple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Exile target creature with the greatest power among creatures on the battlefield.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new ToppleTargetCreature());
    }

    private Topple(final Topple card) {
        super(card);
    }

    @Override
    public Topple copy() {
        return new Topple(this);
    }
}

class ToppleTargetCreature extends TargetCreaturePermanent {

    public ToppleTargetCreature() {
        super();
        setTargetName("creature with the greatest power among creatures on the battlefield");
    }

    public ToppleTargetCreature(final ToppleTargetCreature target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            int maxPower = 0;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent.getPower().getValue() > maxPower) {
                    maxPower = permanent.getPower().getValue();
                }
            }
            Permanent targetPermanent = game.getPermanent(id);
            if (targetPermanent != null) {
                return targetPermanent.getPower().getValue() == maxPower;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        int maxPower = 0;
        List<Permanent> activePermanents = game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if(targetSource == null){
            return possibleTargets;
        }
        for (Permanent permanent : activePermanents) {
            if (permanent.getPower().getValue() > maxPower) {
                maxPower = permanent.getPower().getValue();
            }
        }
        for (Permanent permanent : activePermanents) {
            if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                if (permanent.getPower().getValue() == maxPower) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return !possibleTargets(sourceControllerId, source, game).isEmpty();
    }

    @Override
    public ToppleTargetCreature copy() {
        return new ToppleTargetCreature(this);
    }
}
