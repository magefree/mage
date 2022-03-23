
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSource;

/**
 *
 * @author L_J
 */
public final class BeaconOfDestiny extends CardImpl {

    public BeaconOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: The next time a source of your choice would deal damage to you this turn, that damage is dealt to Beacon of Destiny instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BeaconOfDestinyEffect(), new TapSourceCost()));
    }

    private BeaconOfDestiny(final BeaconOfDestiny card) {
        super(card);
    }

    @Override
    public BeaconOfDestiny copy() {
        return new BeaconOfDestiny(this);
    }
}

class BeaconOfDestinyEffect extends RedirectionEffect {

    private final TargetSource damageSource;

    public BeaconOfDestinyEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next time a source of your choice would deal damage to you this turn, that damage is dealt to {this} instead";
        this.damageSource = new TargetSource();
    }

    public BeaconOfDestinyEffect(final BeaconOfDestinyEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public BeaconOfDestinyEffect copy() {
        return new BeaconOfDestinyEffect(this);
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
        TargetPermanent target = new TargetPermanent();
        target.add(source.getSourceId(), game);
        this.redirectTarget = target;

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
