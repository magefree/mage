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

    public AkoumHellkiteTriggeredAbility(final AkoumHellkiteTriggeredAbility ability) {
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
        if (permanent != null
                && permanent.isLand(game)
                && permanent.isControlledBy(getControllerId())) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null) {
                for (Effect effect : getEffects()) {
                    if (effect instanceof AkoumHellkiteDamageEffect) {
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                    }
                    return true;
                }
            }
        }
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
        Player player = game.getPlayer(source.getFirstTarget());
        if (land != null && player != null) {
            if (land.hasSubtype(SubType.MOUNTAIN, game)) {
                player.damage(2, source.getSourceId(), source, game);
            } else {
                player.damage(1, source.getSourceId(), source, game);
            }
            return true;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (land != null && permanent != null) {
            if (land.hasSubtype(SubType.MOUNTAIN, game)) {
                permanent.damage(2, source.getSourceId(), source, game);
            } else {
                permanent.damage(1, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
