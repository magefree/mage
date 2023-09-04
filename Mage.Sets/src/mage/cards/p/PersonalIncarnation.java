
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author MTGfan
 */
public final class PersonalIncarnation extends CardImpl {

    public PersonalIncarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {0}: The next 1 damage that would be dealt to Personal Incarnation this turn is dealt to its owner instead. Only Personal Incarnation's owner may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PersonalIncarnationRedirectEffect(), new GenericManaCost(0));
        ability.setMayActivate(TargetController.OWNER);
        this.addAbility(ability);
        // When Personal Incarnation dies, its owner loses half their life, rounded up.
        this.addAbility(new DiesSourceTriggeredAbility(new PersonalIncarnationLoseHalfLifeEffect()));
    }

    private PersonalIncarnation(final PersonalIncarnation card) {
        super(card);
    }

    @Override
    public PersonalIncarnation copy() {
        return new PersonalIncarnation(this);
    }
}

class PersonalIncarnationRedirectEffect extends RedirectionEffect {

    public PersonalIncarnationRedirectEffect() {
        super(Duration.EndOfTurn, 1, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next 1 damage that would be dealt to {this} this turn is dealt to its owner instead.";
    }

    private PersonalIncarnationRedirectEffect(final PersonalIncarnationRedirectEffect effect) {
        super(effect);
    }

    @Override
    public PersonalIncarnationRedirectEffect copy() {
        return new PersonalIncarnationRedirectEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            TargetPlayer target = new TargetPlayer();
            target.add(game.getOwnerId(source.getSourceId()), game);
            redirectTarget = target;
            return true;
        }
        return false;
    }
}

class PersonalIncarnationLoseHalfLifeEffect extends OneShotEffect {

    public PersonalIncarnationLoseHalfLifeEffect() {
        super(Outcome.LoseLife);
        staticText = "its owner lose half their life, rounded up";
    }

    private PersonalIncarnationLoseHalfLifeEffect(final PersonalIncarnationLoseHalfLifeEffect effect) {
        super(effect);
    }

    @Override
    public PersonalIncarnationLoseHalfLifeEffect copy() {
        return new PersonalIncarnationLoseHalfLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getSourceId()));
        if (player != null) {
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }
}
