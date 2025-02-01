package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutpaceOblivion extends CardImpl {

    public OutpaceOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this enchantment enters, it deals 5 damage to up to one target creature or planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5, "it"));
        ability.addTarget(new TargetCreatureOrPlaneswalker(0, 1));
        this.addAbility(ability);

        // {2}, Sacrifice this enchantment: It deals 2 damage to each player who doesn't have max speed.
        ability = new SimpleActivatedAbility(new OutpaceOblivionEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OutpaceOblivion(final OutpaceOblivion card) {
        super(card);
    }

    @Override
    public OutpaceOblivion copy() {
        return new OutpaceOblivion(this);
    }
}

class OutpaceOblivionEffect extends OneShotEffect {

    OutpaceOblivionEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 2 damage to each player who doesn't have max speed";
    }

    private OutpaceOblivionEffect(final OutpaceOblivionEffect effect) {
        super(effect);
    }

    @Override
    public OutpaceOblivionEffect copy() {
        return new OutpaceOblivionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getSpeed() < 4) {
                player.damage(2, source, game);
            }
        }
        return true;
    }
}
