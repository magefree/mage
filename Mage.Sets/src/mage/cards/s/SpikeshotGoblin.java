package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SpikeshotGoblin extends CardImpl {

    public SpikeshotGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}, {tap}: Spikeshot Goblin deals damage equal to its power to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpikeshotGoblinEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SpikeshotGoblin(final SpikeshotGoblin card) {
        super(card);
    }

    @Override
    public SpikeshotGoblin copy() {
        return new SpikeshotGoblin(this);
    }
}

class SpikeshotGoblinEffect extends OneShotEffect {
    public SpikeshotGoblinEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage equal to its power to any target";
    }

    private SpikeshotGoblinEffect(final SpikeshotGoblinEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent == null) {
            return false;
        }

        int damage = sourcePermanent.getPower().getValue();

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damage, sourcePermanent.getId(), source, game, false, true);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, sourcePermanent.getId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public SpikeshotGoblinEffect copy() {
        return new SpikeshotGoblinEffect(this);
    }

}
