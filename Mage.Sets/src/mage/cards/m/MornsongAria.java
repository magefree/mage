package mage.cards.m;

import java.util.UUID;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class MornsongAria extends CardImpl {

    public MornsongAria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Players can't draw cards or gain life.
        this.addAbility(new SimpleStaticAbility(new MornsongAriaStaticEffect()));

        // At the beginning of each player's draw step, that player loses 3 life, searches their library for a card, puts it into their hand, then shuffles.
        this.addAbility(new BeginningOfDrawTriggeredAbility(TargetController.EACH_PLAYER, new MornsongAriaTriggerEffect(), false));
    }

    private MornsongAria(final MornsongAria card) {
        super(card);
    }

    @Override
    public MornsongAria copy() {
        return new MornsongAria(this);
    }
}

class MornsongAriaStaticEffect extends ContinuousRuleModifyingEffectImpl {

    MornsongAriaStaticEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players can't draw cards or gain life";
    }

    private MornsongAriaStaticEffect(final MornsongAriaStaticEffect effect) {
        super(effect);
    }

    @Override
    public MornsongAriaStaticEffect copy() {
        return new MornsongAriaStaticEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD
            || event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

class MornsongAriaTriggerEffect extends OneShotEffect {

    public MornsongAriaTriggerEffect() {
        super(Outcome.LoseLife);
        staticText = "that player loses 3 life, searches their library for a card, puts it into their hand, then shuffles";
    }

    private MornsongAriaTriggerEffect(final MornsongAriaTriggerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID activePlayerId = game.getActivePlayerId();
        Player player = game.getPlayer(activePlayerId);
        if (player != null) {
            player.loseLife(3, game, source, false);
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (player.searchLibrary(target, source, game)) {
                player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public MornsongAriaTriggerEffect copy() {
        return new MornsongAriaTriggerEffect(this);
    }
}
