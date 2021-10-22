package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
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
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author stravant
 */
public final class TrespassersCurse extends CardImpl {

    public TrespassersCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever a creature enters the battlefield under enchanted player's control, that player loses 1 life and you gain 1 life.
        this.addAbility(new TrespassersCurseTriggeredAbility());
    }

    private TrespassersCurse(final TrespassersCurse card) {
        super(card);
    }

    @Override
    public TrespassersCurse copy() {
        return new TrespassersCurse(this);
    }
}

class TrespassersCurseTriggeredAbility extends TriggeredAbilityImpl {

    public TrespassersCurseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TrespassersCurseEffect(), false); // false because handled in effect
    }

    public TrespassersCurseTriggeredAbility(final TrespassersCurseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getControllerId(event.getTargetId()).equals(enchantment.getAttachedTo())
                && game.getPermanent(event.getTargetId()).isCreature(game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature enters the battlefield under enchanted player's control, ";
    }

    @Override
    public TrespassersCurseTriggeredAbility copy() {
        return new TrespassersCurseTriggeredAbility(this);
    }

}

class TrespassersCurseEffect extends OneShotEffect {

    public TrespassersCurseEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player loses 1 life and you gain 1 life.";
    }

    public TrespassersCurseEffect(final TrespassersCurseEffect effect) {
        super(effect);
    }

    @Override
    public TrespassersCurseEffect copy() {
        return new TrespassersCurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controllerOfCreature = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controllerOfCreature != null && controller != null) {
            controllerOfCreature.loseLife(1, game, source, false);
            controller.gainLife(1, game, source);
            return true;
        }
        return false;
    }
}
