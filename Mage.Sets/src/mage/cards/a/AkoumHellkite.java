package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AkoumHellkite extends CardImpl {

    public AkoumHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Landfall</i>-Whenever a land enters the battlefield under you control, Akoum Hellkite deals 1 damage to any target.
        // If that land is a Mountain, Akoum Hellkite deals 2 damage to that creature or player instead.
        Ability ability = new AkoumHellkiteTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AkoumHellkite(final AkoumHellkite card) {
        super(card);
    }

    @Override
    public AkoumHellkite copy() {
        return new AkoumHellkite(this);
    }
}

class AkoumHellkiteTriggeredAbility extends TriggeredAbilityImpl {

    public AkoumHellkiteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AkoumHellkiteDamageEffect());
    }

    private AkoumHellkiteTriggeredAbility(final AkoumHellkiteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AkoumHellkiteTriggeredAbility copy() {
        return new AkoumHellkiteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) { return false; }

        if (!permanent.isLand(game) || !permanent.isControlledBy(getControllerId())) { return false; }

        Permanent sourcePermanent = game.getPermanent(getSourceId());
        if (sourcePermanent == null) { return false; }

        for (Effect effect : getEffects()) {
            if (effect instanceof AkoumHellkiteDamageEffect) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
                return true;
            }

        }
        // The Hellkit somehow lost it's damage effect but not it's landfall ability
        return false;
    }

    @Override
    public String getRule() {
        return "<i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, " +
                "{this} deals 1 damage to any target. If that land is a Mountain, {this} deals 2 damage instead.";
    }
}

class AkoumHellkiteDamageEffect extends OneShotEffect {

    public AkoumHellkiteDamageEffect() {
        super(Outcome.Damage);
    }

    public AkoumHellkiteDamageEffect(final AkoumHellkiteDamageEffect effect) {
        super(effect);
    }

    @Override
    public AkoumHellkiteDamageEffect copy() {
        return new AkoumHellkiteDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (land == null) { return false; }

        int damage = land.hasSubtype(SubType.MOUNTAIN, game) ? 2 : 1;

        // Get target for damange
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null && permanent == null) { return false; }

        if (player != null) {
            // Target is a player
            player.damage(damage, source.getSourceId(), source, game);
        } else {
            // Target is a permanent
            permanent.damage(damage, source.getSourceId(), source, game);
        }
        return true;
    }
}
