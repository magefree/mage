package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.game.Game;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.filter.StaticFilters;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 * @author jeffwadsworth
 */
public final class TergridGodOfFright extends ModalDoubleFacesCard {

    public TergridGodOfFright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{3}{B}{B}{B}",
                "Tergrid's Lantern", new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}{B}"
        );

        // 1.
        // Tergrid, God of Fright
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(4), new MageInt(5));

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Whenever an opponent sacrifices a nontoken permanent or discards a permanent card, you may put that card onto the battlefield under your control from their graveyard.
        this.getLeftHalfCard().addAbility(new TergridGodOfFrightTriggeredAbility());

        // 2.
        // Tergrid's Lantern
        // Legendary Artifact
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);

        // Tap: Target player loses 3 life unless they sacrifice a nonland permanent or discard a card.
        Ability tergridsLaternActivatedAbility = new SimpleActivatedAbility(
                new TergridsLaternEffect(), new TapSourceCost()
        );
        tergridsLaternActivatedAbility.addTarget(new TargetPlayer());
        this.getRightHalfCard().addAbility(tergridsLaternActivatedAbility);

        // 3{B}: Untap Tergrid’s Lantern.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl("{3}{B}")));

    }

    private TergridGodOfFright(final TergridGodOfFright card) {
        super(card);
    }

    @Override
    public TergridGodOfFright copy() {
        return new TergridGodOfFright(this);
    }
}

class TergridGodOfFrightTriggeredAbility extends TriggeredAbilityImpl {

    static final String RULE_TEXT = "Whenever an opponent sacrifices a nontoken permanent or discards a permanent card, you may put that card onto the battlefield under your control from their graveyard";

    public TergridGodOfFrightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TergridGodOfFrightEffect());
    }

    public TergridGodOfFrightTriggeredAbility(final TergridGodOfFrightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TergridGodOfFrightTriggeredAbility copy() {
        return new TergridGodOfFrightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT
                    || event.getType() == GameEvent.EventType.DISCARDED_CARD;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null
                    && !(permanent instanceof PermanentToken)
                    && game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                game.getState().setValue(this.getSourceId() + "TergridGodOfFrightControl", ((Card) permanent));
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            Card discardedCard = game.getCard(event.getTargetId());
            if (discardedCard != null
                    && !(discardedCard.isInstantOrSorcery())) {
                game.getState().setValue(this.getSourceId() + "TergridGodOfFrightControl", discardedCard);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return RULE_TEXT + '.';
    }
}

class TergridGodOfFrightEffect extends OneShotEffect {

    public TergridGodOfFrightEffect() {
        super(Outcome.Neutral);
    }

    public TergridGodOfFrightEffect(final TergridGodOfFrightEffect effect) {
        super(effect);
    }

    @Override
    public TergridGodOfFrightEffect copy() {
        return new TergridGodOfFrightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) game.getState().getValue(source.getSourceId() + "TergridGodOfFrightControl");
        if (card != null) {
            System.out.println("In the effect and the card is " + card.getName());
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && card != null) {
            return card.putOntoBattlefield(game, Zone.GRAVEYARD, source, controller.getId());
        }
        return false;
    }
}

class TergridsLaternEffect extends OneShotEffect {

    public TergridsLaternEffect() {
        super(Outcome.Detriment);
        staticText = "Target player loses 3 life unless they sacrifice a nonland permanent or discard a card";
    }

    public TergridsLaternEffect(final TergridsLaternEffect effect) {
        super(effect);
    }

    @Override
    public TergridsLaternEffect copy() {
        return new TergridsLaternEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetedPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if (targetedPlayer != null
                && targetedPlayer.chooseUse(Outcome.Neutral, "Do you wish to sacrifice a nonland permanent to prevent the loss of 3 life?", source, game)) {
            TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
            if (targetedPlayer.choose(Outcome.Detriment, target, source.getSourceId(), game)) {
                Permanent chosenLand = game.getPermanent(target.getFirstTarget());
                if (chosenLand != null) {
                    if (chosenLand.sacrifice(source, game)) {
                        return true;
                    }
                }
            }
        }
        if (targetedPlayer != null
                && targetedPlayer.chooseUse(Outcome.Neutral, "Do you wish to discard a card to prevent the loss of 3 life?", source, game)) {
            TargetCardInHand targetCard = new TargetCardInHand();
            if (targetedPlayer.chooseTarget(Outcome.Detriment, targetCard, source, game)) {
                Card chosenCard = game.getCard(targetCard.getFirstTarget());
                if (chosenCard != null) {
                    if (targetedPlayer.discard(chosenCard, false, source, game)) {
                        return true;
                    }
                }
            }
        }
        if (targetedPlayer != null) {
            targetedPlayer.loseLife(3, game, source, false);
            return true;
        }
        return false;
    }
}
