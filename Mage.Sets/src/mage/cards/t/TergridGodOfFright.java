package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

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
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                        new LoseLifeTargetEffect(3),
                        new OrCost(
                                "sacrifice a nonland permanent or discard a card",
                                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND),
                                new DiscardCardCost()
                        ),
                        "Sacrifice a nonland permanent or discard a card to prevent losing 3 life?"
                ), new TapSourceCost()
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

    TergridGodOfFrightEffect() {
        super(Outcome.Neutral);
    }

    private TergridGodOfFrightEffect(final TergridGodOfFrightEffect effect) {
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
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                // controller gets to choose the order in which the cards enter the battlefield
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}