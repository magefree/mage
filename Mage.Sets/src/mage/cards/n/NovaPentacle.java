package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetOpponentsChoicePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class NovaPentacle extends CardImpl {

    public NovaPentacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: The next time a source of your choice would deal damage to you this turn, that damage is dealt to target creature of an opponent's choice instead
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NovaPentacleEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, new FilterCreaturePermanent(), false));
        this.addAbility(ability);
    }

    private NovaPentacle(final NovaPentacle card) {
        super(card);
    }

    @Override
    public NovaPentacle copy() {
        return new NovaPentacle(this);
    }
}

class NovaPentacleEffect extends RedirectionEffect {

    private final TargetSource damageSource;

    public NovaPentacleEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next time a source of your choice would deal damage to you this turn, that damage is dealt to target creature of an opponent's choice instead";
        this.damageSource = new TargetSource();
    }

    public NovaPentacleEffect(final NovaPentacleEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public NovaPentacleEffect copy() {
        return new NovaPentacleEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.damageSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check source
        MageObject object = game.getObject(event.getSourceId());
        if (object == null) {
            game.informPlayers("Couldn't find source of damage");
            return false;
        }

        if (!object.getId().equals(damageSource.getFirstTarget())
                && (!(object instanceof Spell) || !((Spell) object).getSourceId().equals(damageSource.getFirstTarget()))) {
            return false;
        }
        this.redirectTarget = source.getTargets().get(0);

        //   check player
        Player player = game.getPlayer(event.getTargetId());
        if (player != null) {
            return player.getId().equals(source.getControllerId());
        }
        return false;
    }

}
