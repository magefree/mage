package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GoblinBangchuckers extends CardImpl {

    public GoblinBangchuckers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Flip a coin. If you win the flip, Goblin Bangchuckers deals 2 damage to any target. If you lose the flip, Goblin Bangchuckers deals 2 damage to itself.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinBangchuckersEffect(), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinBangchuckers(final GoblinBangchuckers card) {
        super(card);
    }

    @Override
    public GoblinBangchuckers copy() {
        return new GoblinBangchuckers(this);
    }
}

class GoblinBangchuckersEffect extends OneShotEffect {

    public GoblinBangchuckersEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, {this} deals 2 damage to any target. If you lose the flip, {this} deals 2 damage to itself";
    }

    private GoblinBangchuckersEffect(final GoblinBangchuckersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(source, game, true)) {
                Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
                if (permanent != null) {
                    permanent.damage(2, source.getSourceId(), source, game, false, true);
                    return true;
                }
                Player player = game.getPlayer(targetPointer.getFirst(game, source));
                if (player != null) {
                    player.damage(2, source.getSourceId(), source, game);
                    return true;
                }
            } else {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.damage(2, source.getSourceId(), source, game, false, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GoblinBangchuckersEffect copy() {
        return new GoblinBangchuckersEffect(this);
    }
}
