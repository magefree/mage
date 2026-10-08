package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Riley Jones
 */
public final class JaceMultiverseArchitect extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target planeswalker or creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(CardType.PLANESWALKER.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public JaceMultiverseArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(4);

        // At the beginning of combat on each opponent's turn, they may pay {2}. If they don't, creatures they control can't attack Jaces you control this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                TargetController.OPPONENT,
                new JaceMultiverseArchitectAttackRestrictionEffect(),
                false
        ));

        // +1: Draw two cards, then put a card from your hand on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new JaceMultiverseArchitectDrawEffect(), 1));

        // -3: Exile another target planeswalker or creature you control. Reveal cards from the top of your library until you reveal a creature or planeswalker card. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new LoyaltyAbility(new JaceMultiverseArchitectPolymorphEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Jace, Multiverse Architect can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private JaceMultiverseArchitect(final JaceMultiverseArchitect card) {
        super(card);
    }

    @Override
    public JaceMultiverseArchitect copy() {
        return new JaceMultiverseArchitect(this);
    }
}

class JaceMultiverseArchitectAttackRestrictionEffect extends OneShotEffect {

    JaceMultiverseArchitectAttackRestrictionEffect() {
        super(Outcome.Detriment);
        staticText = "they may pay {2}. If they don't, creatures they control can't attack Jaces you control this turn";
    }

    private JaceMultiverseArchitectAttackRestrictionEffect(final JaceMultiverseArchitectAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public JaceMultiverseArchitectAttackRestrictionEffect copy() {
        return new JaceMultiverseArchitectAttackRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        Cost cost = ManaUtil.createManaCost(2, false);
        if (cost.canPay(source, source, opponent.getId(), game)
                && opponent.chooseUse(Outcome.Benefit, "Pay {2} to allow creatures you control to attack Jaces?", source, game)
                && cost.pay(source, game, source, opponent.getId(), false)) {
            return true;
        }
        game.addEffect(new JaceMultiverseArchitectCantAttackEffect(opponent.getId(), source.getControllerId()), source);
        return true;
    }
}

class JaceMultiverseArchitectCantAttackEffect extends RestrictionEffect {

    private final UUID attackerPlayerId;
    private final UUID defendingControllerId;

    JaceMultiverseArchitectCantAttackEffect(UUID attackerPlayerId, UUID defendingControllerId) {
        super(Duration.EndOfTurn);
        this.attackerPlayerId = attackerPlayerId;
        this.defendingControllerId = defendingControllerId;
    }

    private JaceMultiverseArchitectCantAttackEffect(final JaceMultiverseArchitectCantAttackEffect effect) {
        super(effect);
        this.attackerPlayerId = effect.attackerPlayerId;
        this.defendingControllerId = effect.defendingControllerId;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(attackerPlayerId);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null
                || !planeswalker.hasSubtype(SubType.JACE, game)
                || !planeswalker.isControlledBy(defendingControllerId);
    }

    @Override
    public JaceMultiverseArchitectCantAttackEffect copy() {
        return new JaceMultiverseArchitectCantAttackEffect(this);
    }
}

class JaceMultiverseArchitectDrawEffect extends OneShotEffect {

    JaceMultiverseArchitectDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "draw two cards, then put a card from your hand on the bottom of your library";
    }

    private JaceMultiverseArchitectDrawEffect(final JaceMultiverseArchitectDrawEffect effect) {
        super(effect);
    }

    @Override
    public JaceMultiverseArchitectDrawEffect copy() {
        return new JaceMultiverseArchitectDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(2, source, game);
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(controller.getId(), source, game)) {
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.putCardsOnBottomOfLibrary(card, game, source);
            }
        }
        return true;
    }
}

class JaceMultiverseArchitectPolymorphEffect extends OneShotEffect {

    JaceMultiverseArchitectPolymorphEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "exile another target planeswalker or creature you control. "
                + "Reveal cards from the top of your library until you reveal a creature or planeswalker card. "
                + "Put that card onto the battlefield and the rest on the bottom of your library in a random order";
    }

    private JaceMultiverseArchitectPolymorphEffect(final JaceMultiverseArchitectPolymorphEffect effect) {
        super(effect);
    }

    @Override
    public JaceMultiverseArchitectPolymorphEffect copy() {
        return new JaceMultiverseArchitectPolymorphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            controller.moveCards(permanent, Zone.EXILED, source, game);
        }
        Cards toReveal = new CardsImpl();
        Card toBattlefield = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isCreature(game) || card.isPlaneswalker(game)) {
                toBattlefield = card;
                break;
            }
        }
        controller.revealCards(source, toReveal, game);
        if (toBattlefield != null) {
            toReveal.remove(toBattlefield);
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        }
        controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
