package mage.cards.z;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZombieBoa extends CardImpl {

    public ZombieBoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}: Choose a color. Whenever Zombie Boa becomes blocked by a creature of that color this turn, destroy that creature. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new ZombieBoaEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private ZombieBoa(final ZombieBoa card) {
        super(card);
    }

    @Override
    public ZombieBoa copy() {
        return new ZombieBoa(this);
    }
}

class ZombieBoaEffect extends OneShotEffect {

    ZombieBoaEffect() {
        super(Outcome.Benefit);
        staticText = "choose a color. Whenever {this} becomes blocked by " +
                "a creature of that color this turn, destroy that creature";
    }

    private ZombieBoaEffect(final ZombieBoaEffect effect) {
        super(effect);
    }

    @Override
    public ZombieBoaEffect copy() {
        return new ZombieBoaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        if (player.choose(outcome, choice, game)) {
            ObjectColor color = choice.getColor();
            game.informPlayers(player.getLogName() + " chooses " + color);
            game.addDelayedTriggeredAbility(new ZombieBoaTriggeredAbility(color), source);
            return true;
        }
        return false;
    }
}

class ZombieBoaTriggeredAbility extends DelayedTriggeredAbility {

    private final ObjectColor color;

    ZombieBoaTriggeredAbility(ObjectColor color) {
        super(new DestroyTargetEffect(), Duration.EndOfTurn, false, false);
        this.color = color;
    }

    private ZombieBoaTriggeredAbility(final ZombieBoaTriggeredAbility ability) {
        super(ability);
        this.color = ability.color;
    }

    @Override
    public ZombieBoaTriggeredAbility copy() {
        return new ZombieBoaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isCreature(game) || !permanent.getColor(game).contains(color)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked by a creature of that color this turn, destroy that creature";
    }
}
