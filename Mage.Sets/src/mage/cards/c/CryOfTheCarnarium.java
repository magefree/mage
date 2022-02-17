package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CryOfTheCarnarium extends CardImpl {

    public CryOfTheCarnarium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // All creatures get -2/-2 until end of turn. Exile all creature cards in all graveyards that were put there from the battlefield this turn. If a creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CryOfTheCarnariumExileEffect());
        this.getSpellAbility().addEffect(new CryOfTheCarnariumReplacementEffect());
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private CryOfTheCarnarium(final CryOfTheCarnarium card) {
        super(card);
    }

    @Override
    public CryOfTheCarnarium copy() {
        return new CryOfTheCarnarium(this);
    }
}

class CryOfTheCarnariumExileEffect extends OneShotEffect {

    CryOfTheCarnariumExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile all creature cards in all graveyards that were put there from the battlefield this turn.";
    }

    private CryOfTheCarnariumExileEffect(final CryOfTheCarnariumExileEffect effect) {
        super(effect);
    }

    @Override
    public CryOfTheCarnariumExileEffect copy() {
        return new CryOfTheCarnariumExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        if (controller == null || watcher == null) { return false; }

        Cards cards = new CardsImpl(watcher.getCardsPutIntoGraveyardFromBattlefield(game));
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));

        return controller.moveCards(cards, Zone.EXILED, source, game);
    }
}

class CryOfTheCarnariumReplacementEffect extends ReplacementEffectImpl {

    CryOfTheCarnariumReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = " If a creature would die this turn, exile it instead.";
    }

    private CryOfTheCarnariumReplacementEffect(final CryOfTheCarnariumReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CryOfTheCarnariumReplacementEffect copy() {
        return new CryOfTheCarnariumReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                return player.moveCards(permanent, Zone.EXILED, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getTarget() != null
                && zEvent.getTarget().isCreature(game)
                && zEvent.isDiesEvent();
    }

}
