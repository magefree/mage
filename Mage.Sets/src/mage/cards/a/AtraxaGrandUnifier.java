package mage.cards.a;

import java.util.Arrays;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class AtraxaGrandUnifier extends CardImpl {

    public AtraxaGrandUnifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Atraxa, Grand Unifier enters the battlefield, reveal the top ten cards of your library. For each card type, you may put a card of that type from among the revealed cards into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AtraxaGrandUnifierEffect()));
    }

    private AtraxaGrandUnifier(final AtraxaGrandUnifier card) {
        super(card);
    }

    @Override
    public AtraxaGrandUnifier copy() {
        return new AtraxaGrandUnifier(this);
    }
}

class AtraxaGrandUnifierEffect extends OneShotEffect {

    AtraxaGrandUnifierEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top ten cards of your library. For each card type, " +
                "you may put a card of that type from among the revealed cards into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private AtraxaGrandUnifierEffect(final AtraxaGrandUnifierEffect effect) {
        super(effect);
    }

    @Override
    public AtraxaGrandUnifierEffect copy() {
        return new AtraxaGrandUnifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        player.revealCards(source, cards, game);
        TargetCard target = new AtraxaGrandUnifierTarget();
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.moveCardsToHandWithInfo(toHand, source, game, true);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class AtraxaGrandUnifierTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a card of each card type");

    private static final CardTypeAssignment cardTypeAssigner
            = new CardTypeAssignment(Arrays.stream(CardType.values()).toArray(CardType[]::new));

    AtraxaGrandUnifierTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private AtraxaGrandUnifierTarget(final AtraxaGrandUnifierTarget target) {
        super(target);
    }

    @Override
    public AtraxaGrandUnifierTarget copy() {
        return new AtraxaGrandUnifierTarget(this);
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
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
