package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.BloodToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CitystalkerConnoisseur extends CardImpl {

    public CitystalkerConnoisseur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Citystalker Connoisseur enters the battlefield, target opponent discards a card with the greatest mana value among cards in their hand. Create a Blood token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CitystalkerConnoisseurEffect());
        ability.addEffect(new CreateTokenEffect(new BloodToken()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private CitystalkerConnoisseur(final CitystalkerConnoisseur card) {
        super(card);
    }

    @Override
    public CitystalkerConnoisseur copy() {
        return new CitystalkerConnoisseur(this);
    }
}

class CitystalkerConnoisseurEffect extends OneShotEffect {

    CitystalkerConnoisseurEffect() {
        super(Outcome.Discard);
        staticText = "target opponent discards a card with the greatest mana value among cards in their hand";
    }

    private CitystalkerConnoisseurEffect(final CitystalkerConnoisseurEffect effect) {
        super(effect);
    }

    @Override
    public CitystalkerConnoisseurEffect copy() {
        return new CitystalkerConnoisseurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        if (player.getHand().size() == 1) {
            player.discard(player.getHand(), false, source, game);
            return true;
        }
        int maxValue = player
                .getHand()
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        Cards cards = new CardsImpl(player.getHand());
        cards.removeIf(uuid -> game.getCard(uuid).getManaValue() < maxValue);
        if (cards.size() == 1) {
            player.discard(cards, false, source, game);
            return true;
        }
        TargetCardInHand target = new TargetCardInHand();
        player.choose(outcome, cards, target, game);
        player.discard(cards.get(target.getFirstTarget(), game), false, source, game);
        return true;
    }
}
