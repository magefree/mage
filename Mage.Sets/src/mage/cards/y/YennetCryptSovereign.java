package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class YennetCryptSovereign extends CardImpl {

    public YennetCryptSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Yennet, Crypt Sovereign attacks, reveal the top card of your library. If that card's converted mana cost is odd, you may cast it without paying its mana cost. Otherwise, draw a card.
        this.addAbility(new AttacksTriggeredAbility(
                new YennetCryptSovereignEffect(), false
        ));
    }

    public YennetCryptSovereign(final YennetCryptSovereign card) {
        super(card);
    }

    @Override
    public YennetCryptSovereign copy() {
        return new YennetCryptSovereign(this);
    }
}

class YennetCryptSovereignEffect extends OneShotEffect {

    public YennetCryptSovereignEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library. "
                + "If that card's converted mana cost is odd, "
                + "you may cast it without paying its mana cost. "
                + "Otherwise, draw a card";
    }

    public YennetCryptSovereignEffect(final YennetCryptSovereignEffect effect) {
        super(effect);
    }

    @Override
    public YennetCryptSovereignEffect copy() {
        return new YennetCryptSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (card.getConvertedManaCost() % 2 == 1) {
            if (player.chooseUse(outcome, "Cast " + card.getLogName() + " without paying its mana cost?", source, game)) {
                player.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
            }
        } else {
            player.drawCards(1, game);
        }
        return true;
    }
}
