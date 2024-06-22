package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.assignment.common.TypeAssignment;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EivorWolfKissed extends CardImpl {

    public EivorWolfKissed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Eivor, Wolf-Kissed deals combat damage to a player, you mill that many cards. You may put a Saga card and/or a land card from among them onto the battlefield.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new EivorWolfKissedEffect(), false));
    }

    private EivorWolfKissed(final EivorWolfKissed card) {
        super(card);
    }

    @Override
    public EivorWolfKissed copy() {
        return new EivorWolfKissed(this);
    }
}

class EivorWolfKissedEffect extends OneShotEffect {

    EivorWolfKissedEffect() {
        super(Outcome.Benefit);
        staticText = "you mill that many cards. You may put a Saga card " +
                "and/or a land card from among them onto the battlefield";
    }

    private EivorWolfKissedEffect(final EivorWolfKissedEffect effect) {
        super(effect);
    }

    @Override
    public EivorWolfKissedEffect copy() {
        return new EivorWolfKissedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = player.millCards(damage, source, game);
        TargetCard target = new EivorWolfKissedTarget();
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}

class EivorWolfKissedTarget extends TargetCard {

    private static final FilterCard filter
            = new FilterCard("a Saga card and/or a land card");

    static {
        filter.add(Predicates.or(
                SubType.SAGA.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    private static final TypeAssignment typeAssigner = new TypeAssignment(SubType.SAGA, CardType.LAND);

    EivorWolfKissedTarget() {
        super(0, 2, Zone.ALL, filter);
        notTarget = true;
    }

    private EivorWolfKissedTarget(final EivorWolfKissedTarget target) {
        super(target);
    }

    @Override
    public EivorWolfKissedTarget copy() {
        return new EivorWolfKissedTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return typeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
