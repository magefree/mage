
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class GeneralsRegalia extends CardImpl {

    public GeneralsRegalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}: The next time a source of your choice would deal damage to you this turn, that damage is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GeneralsRegaliaEffect(), new GenericManaCost(3));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private GeneralsRegalia(final GeneralsRegalia card) {
        super(card);
    }

    @Override
    public GeneralsRegalia copy() {
        return new GeneralsRegalia(this);
    }
}

class GeneralsRegaliaEffect extends RedirectionEffect {

    private final TargetSource damageSource;

    public GeneralsRegaliaEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next time a source of your choice would deal damage to you this turn, that damage is dealt to target creature you control instead";
        this.damageSource = new TargetSource();
    }

    public GeneralsRegaliaEffect(final GeneralsRegaliaEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public GeneralsRegaliaEffect copy() {
        return new GeneralsRegaliaEffect(this);
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
            if (player.getId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

}
