package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.common.DiscardCardCost;
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
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetSacrifice;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
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
                                new TergridsLanternCost(StaticFilters.FILTER_PERMANENT_NON_LAND),
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

// Based on SacrificeTargetCost
class TergridsLanternCost extends CostImpl implements SacrificeCost {

    private final List<Permanent> permanents = new ArrayList<>();

    /**
     * Sacrifice a permanent matching the filter:
     *
     * @param filter can be generic, will automatically add article and sacrifice predicates
     */
    public TergridsLanternCost(FilterPermanent filter) {
        this(1, filter);
    }

    /**
     * Sacrifice N permanents matching the filter:
     *
     * @param filter can be generic, will automatically add sacrifice predicates
     */
    public TergridsLanternCost(int numToSac, FilterPermanent filter) {
        this(new TargetSacrifice(numToSac, filter));
    }

    public TergridsLanternCost(TargetSacrifice target) {
        this.addTarget(target);
        target.setRequired(false); // can be canceled
        this.text = "sacrifice " + makeText(target);
    }

    public TergridsLanternCost(TergridsLanternCost cost) {
        super(cost);
        for (Permanent permanent : cost.permanents) {
            this.permanents.add(permanent.copy());
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        UUID payingPlayer = source.getFirstTarget();
        // can be cancelled by payer
        if (this.getTargets().choose(Outcome.Sacrifice, payingPlayer, source.getSourceId(), source, game)) {
            for (UUID targetId : this.getTargets().get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                addSacrificeTarget(game, permanent);
                paid |= permanent.sacrifice(source, game);
            }
            if (!paid && this.getTargets().get(0).getNumberOfTargets() == 0) {
                paid = true; // e.g. for Devouring Rage
            }
        }
        return paid;
    }

    /**
     * For storing additional info upon selecting permanents to sacrifice
     */
    protected void addSacrificeTarget(Game game, Permanent permanent) {
        permanents.add(permanent.copy());
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        UUID payingPlayer = source.getFirstTarget();

        int validTargets = 0;
        int neededTargets = this.getTargets().get(0).getNumberOfTargets();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(((TargetPermanent) this.getTargets().get(0)).getFilter(), controllerId, source, game)) {
            if (game.getPlayer(payingPlayer).canPaySacrificeCost(permanent, source, controllerId, game)) {
                validTargets++;
                if (validTargets >= neededTargets) {
                    return true;
                }
            }
        }
        // solves issue #8097, if a sacrifice cost is optional and you don't have valid targets, then the cost can be paid
        if (validTargets == 0 && this.getTargets().get(0).getMinNumberOfTargets() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public TergridsLanternCost copy() {
        return new TergridsLanternCost(this);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }

    private static String makeText(TargetSacrifice target) {
        if (target.getMinNumberOfTargets() != target.getMaxNumberOfTargets()) {
            return target.getTargetName();
        }
        if (target.getNumberOfTargets() == 1
                || target.getTargetName().startsWith("a ")
                || target.getTargetName().startsWith("an ")) {
            return CardUtil.addArticle(target.getTargetName());
        }
        return CardUtil.numberToText(target.getNumberOfTargets()) + ' ' + target.getTargetName();
    }

}
