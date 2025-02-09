package mage.designations;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class Speed extends Designation {

    public Speed() {
        super(DesignationType.SPEED);
        addAbility(new SpeedTriggeredAbility());

        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_SPEED, null);
        if (foundInfo != null) {
            this.setExpansionSetCode(foundInfo.getSetCode());
            this.setUsesVariousArt(true);
            this.setCardNumber("");
            this.setImageFileName(""); // use default
            this.setImageNumber(foundInfo.getImageNumber());
        } else {
            // how-to fix: add image to the tokens-database TokenRepository->loadXmageTokens
            throw new IllegalArgumentException("Wrong code usage: can't find xmage token info for: " + TokenRepository.XMAGE_IMAGE_NAME_SPEED);
        }
    }

    private Speed(final Speed card) {
        super(card);
    }

    @Override
    public Speed copy() {
        return new Speed(this);
    }
}

class SpeedTriggeredAbility extends TriggeredAbilityImpl {

    SpeedTriggeredAbility() {
        super(Zone.ALL, new SpeedEffect());
        setTriggersLimitEachTurn(1);
    }

    private SpeedTriggeredAbility(final SpeedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpeedTriggeredAbility copy() {
        return new SpeedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId())
                && game
                .getOpponents(getControllerId())
                .contains(event.getTargetId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return Optional
                .ofNullable(getControllerId())
                .map(game::getPlayer)
                .map(Player::getSpeed)
                .map(x -> x < 4)
                .orElse(false);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more opponents lose life during your turn, if your speed is less than 4, " +
                "increase your speed by 1. This ability triggers only once each turn.";
    }
}

class SpeedEffect extends OneShotEffect {

    SpeedEffect() {
        super(Outcome.Benefit);
    }

    private SpeedEffect(final SpeedEffect effect) {
        super(effect);
    }

    @Override
    public SpeedEffect copy() {
        return new SpeedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getControllerId())
                .map(game::getPlayer)
                .ifPresent(player -> player.increaseSpeed(game));
        return true;
    }
}
