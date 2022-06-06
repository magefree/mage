
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.GameLog;

/**
 *
 * @author LevelX2
 */
public final class AlhammarretHighArbiter extends CardImpl {

    public AlhammarretHighArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As Alhammarret, High Arbiter enters the battlefield, each opponent reveals their hand. You choose the name of a nonland card revealed this way.
        // Your opponents can't cast spells with the chosen name.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AlhammarretHighArbiterEffect(), "")));
    }

    private AlhammarretHighArbiter(final AlhammarretHighArbiter card) {
        super(card);
    }

    @Override
    public AlhammarretHighArbiter copy() {
        return new AlhammarretHighArbiter(this);
    }
}

class AlhammarretHighArbiterEffect extends OneShotEffect {

    public AlhammarretHighArbiterEffect() {
        super(Outcome.Benefit);
        this.staticText = "As {this} enters the battlefield, each opponent reveals their hand. You choose the name of a nonland card revealed this way."
                + "<br>Your opponents can't cast spells with the chosen name";
    }

    public AlhammarretHighArbiterEffect(final AlhammarretHighArbiterEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretHighArbiterEffect copy() {
        return new AlhammarretHighArbiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards revealedCards = new CardsImpl();
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    Cards cards = new CardsImpl(opponent.getHand());
                    opponent.revealCards(opponent.getName() + "'s hand", cards, game);
                    revealedCards.addAll(cards);
                }
            }
            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card from an opponents hand"));
            controller.chooseTarget(Outcome.Benefit, revealedCards, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                game.informPlayers("The chosen card name is [" + GameLog.getColoredObjectName(card) + ']');
                Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
                if (sourcePermanent == null) {
                    sourcePermanent = game.getPermanentEntering(source.getSourceId());
                }
                if (sourcePermanent != null) {
                    sourcePermanent.addInfo("chosen card name", CardUtil.addToolTipMarkTags("Chosen card name: " + card.getName()), game);
                }
                game.addEffect(new AlhammarretHighArbiterCantCastEffect(card.getName()), source);
            }
            return true;

        }
        return false;
    }
}

class AlhammarretHighArbiterCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    String cardName;
    int zoneChangeCounter;

    public AlhammarretHighArbiterCantCastEffect(String cardName) {
        super(Duration.Custom, Outcome.Benefit);
        this.cardName = cardName;
        staticText = "Your opponents can't cast spells with the chosen name";
    }

    public AlhammarretHighArbiterCantCastEffect(final AlhammarretHighArbiterCantCastEffect effect) {
        super(effect);
        this.cardName = effect.cardName;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        zoneChangeCounter = game.getState().getZoneChangeCounter(source.getId());
    }

    @Override
    public AlhammarretHighArbiterCantCastEffect copy() {
        return new AlhammarretHighArbiterCantCastEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return game.getState().getZoneChangeCounter(source.getId()) != zoneChangeCounter;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You may not cast a card named " + cardName + " (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
}
