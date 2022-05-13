package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class GaeasWill extends CardImpl {

    public GaeasWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setGreen(true);

        // Suspend 4—{G}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{G}"), this));

        // Until end of turn, you may play land cards and cast spells from your graveyard.
        this.getSpellAbility().addEffect(new GaeasWillGraveyardEffect());

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.getSpellAbility().addEffect(new GaeassWillReplacementEffect());
    }

    private GaeasWill(final GaeasWill card) {
        super(card);
    }

    @Override
    public GaeasWill copy() {
        return new GaeasWill(this);
    }
}

class GaeasWillGraveyardEffect extends ContinuousEffectImpl {

    public GaeasWillGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public GaeasWillGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.staticText = "Until end of turn, you may play lands and cast spells from your graveyard";
    }

    private GaeasWillGraveyardEffect(final GaeasWillGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public GaeasWillGraveyardEffect copy() {
        return new GaeasWillGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setPlayCardsFromGraveyard(true);
            return true;
        }
        return false;
    }
}

class GaeassWillReplacementEffect extends ReplacementEffectImpl {

    public GaeassWillReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        this.staticText = "<br>If a card would be put into your graveyard from anywhere this turn, exile that card instead";
    }

    public GaeassWillReplacementEffect(final GaeassWillReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GaeassWillReplacementEffect copy() {
        return new GaeassWillReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isOwnedBy(source.getControllerId())) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (!(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }
        return false;
    }
}
