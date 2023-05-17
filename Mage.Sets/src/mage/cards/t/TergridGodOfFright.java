package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TergridGodOfFright extends ModalDoubleFacedCard {

    public TergridGodOfFright(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{3}{B}{B}",
                "Tergrid's Lantern",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}{B}"
        );

        // 1.
        // Tergrid, God of Fright
        // Legendary Creature - God
        this.getLeftHalfCard().setPT(4, 5);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility(false));

        // Whenever an opponent sacrifices a nontoken permanent or discards a permanent card, you may put that card onto the battlefield under your control from their graveyard.
        this.getLeftHalfCard().addAbility(new TergridGodOfFrightTriggeredAbility());

        // 2.
        // Tergrid's Lantern
        // Legendary Artifact
        // {T}: Target player loses 3 life unless they sacrifice a nonland permanent or discard a card.
        Ability tergridsLaternActivatedAbility = new SimpleActivatedAbility(
                new TergridsLaternEffect(), new TapSourceCost()
        );
        tergridsLaternActivatedAbility.addTarget(new TargetPlayer());
        this.getRightHalfCard().addAbility(tergridsLaternActivatedAbility);

        // {3}{B}: Untap Tergrid’s Lantern.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl<>("{3}{B}")));

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

    private static final String RULE_TEXT = "Whenever an opponent sacrifices a nontoken permanent or discards a permanent card, you may put that card from a graveyard onto the battlefield under your control";

    public TergridGodOfFrightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TergridGodOfFrightEffect(), true);
    }

    private TergridGodOfFrightTriggeredAbility(final TergridGodOfFrightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TergridGodOfFrightTriggeredAbility copy() {
        return new TergridGodOfFrightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT
                || event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        // it must be in the graveyard IE: Rest in Peace effect
        switch (event.getType()) {
            case SACRIFICED_PERMANENT:
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent == null
                        || permanent instanceof PermanentToken
                        || game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD) {
                    return false;
                }
                break;
            case DISCARDED_CARD:
                Card discardedCard = game.getCard(event.getTargetId());
                if (discardedCard == null
                        || !discardedCard.isPermanent(game)
                        || game.getState().getZone(discardedCard.getId()) != Zone.GRAVEYARD) {
                    return false;
                }
                break;
            default:
                return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                // controller gets to choose the order in which the cards enter the battlefield
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}

class TergridsLaternEffect extends OneShotEffect {

    private static final String SACRIFICE_CHOICE = "Sacrifice a nonland permanent";
    private static final String DISCARD_CHOICE = "Discard a card";
    private static final String LIFE_LOSS_CHOICE = "Lose 3 life";

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
        if (targetedPlayer == null) {
            return false;
        }

        // AI hint to discard/sacrifice before die
        Outcome aiOutcome = (targetedPlayer.getLife() <= 3 * 2) ? Outcome.Benefit : Outcome.Detriment;

        Set<String> choiceSet = new HashSet<>();
        if (game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND, targetedPlayer.getId(), source, game) > 0) {
            choiceSet.add(SACRIFICE_CHOICE);
        }
        if (targetedPlayer.getHand().size() > 0) {
            choiceSet.add(DISCARD_CHOICE);
        }
        choiceSet.add(LIFE_LOSS_CHOICE);
        String chosen;
        if (choiceSet.size() > 1) {
            Choice choice = new ChoiceImpl(true);
            choice.setChoices(choiceSet);
            targetedPlayer.choose(aiOutcome, choice, game);
            chosen = choice.getChoice();
            if (chosen == null) {
                // on disconnect
                chosen = LIFE_LOSS_CHOICE;
            }
        } else {
            chosen = LIFE_LOSS_CHOICE;
        }
        switch (chosen) {
            case SACRIFICE_CHOICE:
                TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                target.setNotTarget(true);
                targetedPlayer.choose(Outcome.Sacrifice, target, source, game);
                Permanent chosenLand = game.getPermanent(target.getFirstTarget());
                return chosenLand != null && chosenLand.sacrifice(source, game);
            case DISCARD_CHOICE:
                return targetedPlayer.discard(1, false, false, source, game).size() > 0;
            case LIFE_LOSS_CHOICE:
                return targetedPlayer.loseLife(3, game, source, false) > 0;
        }
        return false;
    }
}
