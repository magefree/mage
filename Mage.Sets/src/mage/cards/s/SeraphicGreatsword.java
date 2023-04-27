package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SeraphicGreatsword extends CardImpl {

    public SeraphicGreatsword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2)));

        // Whenever equipped creature attacks the player with the most life or tied for most life, create a 4/4 white Angel creature token with flying that's tapped and attacking that player.
        this.addAbility(new SeraphicGreatswordTriggeredAbility());

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), new TargetControlledCreaturePermanent(), false));
    }

    private SeraphicGreatsword(final SeraphicGreatsword card) {
        super(card);
    }

    @Override
    public SeraphicGreatsword copy() {
        return new SeraphicGreatsword(this);
    }
}

class SeraphicGreatswordTriggeredAbility extends TriggeredAbilityImpl {

    public SeraphicGreatswordTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public SeraphicGreatswordTriggeredAbility(final SeraphicGreatswordTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public SeraphicGreatswordTriggeredAbility copy() {
        return new SeraphicGreatswordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.sourceId);
        if (equipment == null
                || equipment.getAttachedTo() == null
                || !event.getSourceId().equals(equipment.getAttachedTo())) {
            return false;
        }
        Player player = game.getPlayer(event.getTargetId());
        if (player == null
                || player.getLife() < game
                .getState()
                .getPlayersInRange(getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .max()
                .orElse(0)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new SeraphicGreatswordEffect(event.getTargetId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks the player with the most life or tied for most life, " +
                "create a 4/4 white Angel creature token with flying that's tapped and attacking that player.";
    }
}

class SeraphicGreatswordEffect extends OneShotEffect {

    private final UUID playerId;

    SeraphicGreatswordEffect(UUID playerId) {
        super(Outcome.Benefit);
        this.playerId = playerId;
    }

    private SeraphicGreatswordEffect(final SeraphicGreatswordEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
    }

    @Override
    public SeraphicGreatswordEffect copy() {
        return new SeraphicGreatswordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AngelToken()
                .putOntoBattlefield(1, game, source, source.getControllerId(), true, true, playerId);
    }
}
