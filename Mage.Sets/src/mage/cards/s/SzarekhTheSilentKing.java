package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SzarekhTheSilentKing extends CardImpl {

    public SzarekhTheSilentKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // My Will Be Done -- Whenever Szarekh, the Silent King attacks, mill three cards. You may put an artifact creature card or Vehicle card from among the cards milled this way into your hand.
        this.addAbility(new AttacksTriggeredAbility(new SzarekhTheSilentKingEffect()).withFlavorWord("My Will Be Done"));
    }

    private SzarekhTheSilentKing(final SzarekhTheSilentKing card) {
        super(card);
    }

    @Override
    public SzarekhTheSilentKing copy() {
        return new SzarekhTheSilentKing(this);
    }
}

class SzarekhTheSilentKingEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact creature card or Vehicle card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.ARTIFACT.getPredicate(),
                        CardType.CREATURE.getPredicate()
                ), SubType.VEHICLE.getPredicate()
        ));
    }

    SzarekhTheSilentKingEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. You may put an artifact creature card " +
                "or Vehicle card from among the cards milled this way into your hand";
    }

    private SzarekhTheSilentKingEffect(final SzarekhTheSilentKingEffect effect) {
        super(effect);
    }

    @Override
    public SzarekhTheSilentKingEffect copy() {
        return new SzarekhTheSilentKingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(3, source, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(0, 1, filter);
        player.choose(outcome, cards, target, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
