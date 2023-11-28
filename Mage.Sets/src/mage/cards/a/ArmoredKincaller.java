package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmoredKincaller extends CardImpl {

    public ArmoredKincaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Armored Kincaller enters the battlefield, you may reveal a Dinosaur card from your hand. If you do or if you control another Dinosaur, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArmoredKincallerEffect()));
    }

    private ArmoredKincaller(final ArmoredKincaller card) {
        super(card);
    }

    @Override
    public ArmoredKincaller copy() {
        return new ArmoredKincaller(this);
    }
}

class ArmoredKincallerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Dinosaur card");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DINOSAUR, "");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    ArmoredKincallerEffect() {
        super(Outcome.Benefit);
        staticText = "you may reveal a Dinosaur card from your hand. " +
                "If you do or if you control another Dinosaur, you gain 3 life";
    }

    private ArmoredKincallerEffect(final ArmoredKincallerEffect effect) {
        super(effect);
    }

    @Override
    public ArmoredKincallerEffect copy() {
        return new ArmoredKincallerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
        }
        if (card != null || game.getBattlefield().contains(filter2, source, game, 1)) {
            player.gainLife(3, game, source);
        }
        return true;
    }
}
