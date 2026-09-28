package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class FblthpImpossiblyLost extends CardImpl {

    public FblthpImpossiblyLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When one or more of your opponents are dealt combat damage during your turn, draw two cards. If your library has no cards in it, you win the game. Fblthp's owner shuffles him into their library.
        this.addAbility(new FblthpImpossiblyLostTriggeredAbility());
    }

    private FblthpImpossiblyLost(final FblthpImpossiblyLost card) {
        super(card);
    }

    @Override
    public FblthpImpossiblyLost copy() {
        return new FblthpImpossiblyLost(this);
    }
}

class FblthpImpossiblyLostTriggeredAbility extends TriggeredAbilityImpl {

    FblthpImpossiblyLostTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2));
        setTriggerPhrase("When one or more of your opponents are dealt combat damage during your turn, ");
        addEffect(new FblthpImpossiblyLostEffect());
        addEffect(new ShuffleIntoLibrarySourceEffect().setText("{this}'s owner shuffles him into their library"));
    }

    private FblthpImpossiblyLostTriggeredAbility(final FblthpImpossiblyLostTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getActivePlayerId())
                || !game.getOpponents(getControllerId()).contains(event.getTargetId())
                || !((DamagedBatchForOnePlayerEvent) event).isCombatDamage()) {
            return false;
        }
        return true;
    }

    @Override
    public FblthpImpossiblyLostTriggeredAbility copy() {
        return new FblthpImpossiblyLostTriggeredAbility(this);
    }
}

class FblthpImpossiblyLostEffect extends OneShotEffect {

    FblthpImpossiblyLostEffect() {
        super(Outcome.Benefit);
        staticText = "If your library has no cards in it, you win the game";
    }

    private FblthpImpossiblyLostEffect(final FblthpImpossiblyLostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() == 0) {
            player.won(game);
        }
        return true;
    }

    @Override
    public FblthpImpossiblyLostEffect copy() {
        return new FblthpImpossiblyLostEffect(this);
    }
}
