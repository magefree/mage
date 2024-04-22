package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WinotaJoinerOfForces extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("non-Human creature you control");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public WinotaJoinerOfForces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a non-Human creature you control attacks, look at the top six cards of your library. You may put a Human creature card from among them onto the battlefield tapped and attacking. It gains indestructible until end of turn. Put the rest of the cards on the bottom of your library in a random order.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new WinotaJoinerOfForcesEffect(), false, filter
        ));
    }

    private WinotaJoinerOfForces(final WinotaJoinerOfForces card) {
        super(card);
    }

    @Override
    public WinotaJoinerOfForces copy() {
        return new WinotaJoinerOfForces(this);
    }
}

class WinotaJoinerOfForcesEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("Human creature card");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    WinotaJoinerOfForcesEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. " +
                "You may put a Human creature card from among them onto the battlefield tapped and attacking. " +
                "It gains indestructible until end of turn. " +
                "Put the rest of the cards on the bottom of your library in a random order.";
    }

    private WinotaJoinerOfForcesEffect(final WinotaJoinerOfForcesEffect effect) {
        super(effect);
    }

    @Override
    public WinotaJoinerOfForcesEffect copy() {
        return new WinotaJoinerOfForcesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, targetCardInLibrary, source, game);
        Card card = game.getCard(targetCardInLibrary.getFirstTarget());
        if (card == null || !player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, true, null
        )) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        cards.remove(card);
        game.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return player.putCardsOnBottomOfLibrary(cards, game, source, false);
    }
}