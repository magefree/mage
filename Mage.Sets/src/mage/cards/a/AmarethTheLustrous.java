package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmarethTheLustrous extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AmarethTheLustrous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another permanent enters the battlefield under your control, look at the top card of your library. If it shares a card type with that permanent, you may reveal that card and put it into your hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new AmarethTheLustrousEffect(), filter));
    }

    private AmarethTheLustrous(final AmarethTheLustrous card) {
        super(card);
    }

    @Override
    public AmarethTheLustrous copy() {
        return new AmarethTheLustrous(this);
    }
}

class AmarethTheLustrousEffect extends OneShotEffect {

    AmarethTheLustrousEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it shares a card type with that permanent, " +
                "you may reveal that card and put it into your hand";
    }

    private AmarethTheLustrousEffect(final AmarethTheLustrousEffect effect) {
        super(effect);
    }

    @Override
    public AmarethTheLustrousEffect copy() {
        return new AmarethTheLustrousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of library", card, game);
        Object obj = getValue("permanentEnteringBattlefield");
        Permanent permanent = null;
        if (obj instanceof Permanent) {
            permanent = (Permanent) obj;
        }
        if (permanent == null
                || card.getCardType(game).stream().noneMatch(permanent.getCardType(game)::contains)
                || !player.chooseUse(Outcome.DrawCard, "Reveal " + card.getName() + " and put it into your hand?", source, game)) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}
