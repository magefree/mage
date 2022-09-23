package mage.cards.o;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DevilToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.util.functions.StackObjectCopyApplier;

/**
 *
 * @author weirddan455
 */
public final class ObNixilisTheAdversary extends CardImpl {

    public ObNixilisTheAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIXILIS);
        this.setStartingLoyalty(3);

        // Casualty X. The copy isn't legendary and has starting loyalty X.
        this.addAbility(new ObNixilisTheAdversaryCasualtyAbility(this));

        // +1: Each opponent loses 2 life unless they discard a card. If you control a Demon or Devil, you gain 2 life.
        this.addAbility(new LoyaltyAbility(new ObNixilisTheAdversaryDiscardEffect(), 1));

        // −2: Create a 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DevilToken()), -2));

        // −7: Target player draws seven cards and loses 7 life.
        Ability ability = new LoyaltyAbility(new DrawCardTargetEffect(7), -7);
        ability.addEffect(new LoseLifeTargetEffect(7).setText("and loses 7 life"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ObNixilisTheAdversary(final ObNixilisTheAdversary card) {
        super(card);
    }

    @Override
    public ObNixilisTheAdversary copy() {
        return new ObNixilisTheAdversary(this);
    }
}

// TODO: Would be nice to refactor into Casualty ability so this doesn't have a custom implementation to maintain
class ObNixilisTheAdversaryCasualtyAbility extends StaticAbility {

    public ObNixilisTheAdversaryCasualtyAbility(Card card) {
        super(Zone.ALL, new InfoEffect(
                "Casualty X. The copy isn't legendary and has starting loyalty X. <i>(As you cast this spell, " +
                        "you may sacrifice a creature with power X. " +
                        "When you do, copy this spell. The copy becomes a token.)</i>"
        ));
        card.getSpellAbility().addCost(new ObNixilisTheAdversaryCost());
        this.setRuleAtTheTop(true);
    }

    private ObNixilisTheAdversaryCasualtyAbility(final ObNixilisTheAdversaryCasualtyAbility ability) {
        super(ability);
    }

    @Override
    public ObNixilisTheAdversaryCasualtyAbility copy() {
        return new ObNixilisTheAdversaryCasualtyAbility(this);
    }
}

class ObNixilisTheAdversaryCost extends SacrificeTargetCost {

    public ObNixilisTheAdversaryCost() {
        super(new TargetControlledPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true));
        this.text = "";
    }

    private ObNixilisTheAdversaryCost(final ObNixilisTheAdversaryCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!super.pay(ability, game, source, controllerId, noMana, costToPay)) {
            return false;
        }
        List<Permanent> sacrificedPermanents = getPermanents();
        if (!sacrificedPermanents.isEmpty()) {
            StackObjectCopyApplier applier = new ObNixilisTheAdversaryApplier(sacrificedPermanents.get(0).getPower().getValue());
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                    new ObNixilisTheAdversaryCopyEffect(applier), false, "when you do, copy this spell"
            ), source);
        }
        return true;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public ObNixilisTheAdversaryCost copy() {
        return new ObNixilisTheAdversaryCost(this);
    }
}

class ObNixilisTheAdversaryCopyEffect extends OneShotEffect {

    private final StackObjectCopyApplier applier;

    public ObNixilisTheAdversaryCopyEffect(StackObjectCopyApplier applier) {
        super(Outcome.Copy);
        this.applier = applier;
        this.staticText = "copy {this}";
    }

    private ObNixilisTheAdversaryCopyEffect(final ObNixilisTheAdversaryCopyEffect effect) {
        super(effect);
        this.applier = effect.applier;
    }

    @Override
    public ObNixilisTheAdversaryCopyEffect copy() {
        return new ObNixilisTheAdversaryCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(source.getSourceId());
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, 1, applier);
        return true;
    }
}

class ObNixilisTheAdversaryApplier implements StackObjectCopyApplier {

    private final int loyalty;

    public ObNixilisTheAdversaryApplier(int loyalty) {
        this.loyalty = loyalty;
    }

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
        stackObject.getSuperType().remove(SuperType.LEGENDARY);
        stackObject.setStartingLoyalty(loyalty);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}

class ObNixilisTheAdversaryDiscardEffect extends OneShotEffect {

    public ObNixilisTheAdversaryDiscardEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Each opponent loses 2 life unless they discard a card. If you control a Demon or Devil, you gain 2 life";
    }

    private ObNixilisTheAdversaryDiscardEffect(final ObNixilisTheAdversaryDiscardEffect effect) {
        super(effect);
    }

    @Override
    public ObNixilisTheAdversaryDiscardEffect copy() {
        return new ObNixilisTheAdversaryDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(controllerId)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            if (opponent.getHand().isEmpty() || !opponent.chooseUse(
                    Outcome.Discard, "Discard a card or lose 2 life?",
                    null, "Discard Card", "Lose 2 Life", source, game)
                    || opponent.discard(1, false, false, source, game).isEmpty()) {
                opponent.loseLife(2, game, source, false);
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controllerId)) {
            if (permanent.hasSubtype(SubType.DEMON, game) || permanent.hasSubtype(SubType.DEVIL, game)) {
                controller.gainLife(2, game, source);
                break;
            }
        }
        return true;
    }
}
