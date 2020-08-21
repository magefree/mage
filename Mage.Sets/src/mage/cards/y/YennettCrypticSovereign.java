package mage.cards.y;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
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
public final class YennettCrypticSovereign extends CardImpl {

    public YennettCrypticSovereign(UUID ownerId, CardSetInfo setInfo) {
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

        // Whenever Yennett, Cryptic Sovereign attacks, reveal the top card of your library. If that card's 
        // converted mana cost is odd, you may cast it without paying its mana cost. Otherwise, draw a card.
        this.addAbility(new AttacksTriggeredAbility(
                new YennettCrypticSovereignEffect(), false
        ));
    }

    public YennettCrypticSovereign(final YennettCrypticSovereign card) {
        super(card);
    }

    @Override
    public YennettCrypticSovereign copy() {
        return new YennettCrypticSovereign(this);
    }
}

class YennettCrypticSovereignEffect extends OneShotEffect {

    public YennettCrypticSovereignEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library. "
                + "If that card's converted mana cost is odd, "
                + "you may cast it without paying its mana cost. "
                + "Otherwise, draw a card";
    }

    public YennettCrypticSovereignEffect(final YennettCrypticSovereignEffect effect) {
        super(effect);
    }

    @Override
    public YennettCrypticSovereignEffect copy() {
        return new YennettCrypticSovereignEffect(this);
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
                player.cast(card.getSpellAbility(), game, true, new ApprovingObject(source, game));
            } else {
                /*
                7/13/2018 | If the revealed card doesn’t have an odd converted mana cost or if that card does but you 
                choose not to cast it, you draw a card. Keep in mind that revealing a card doesn’t cause it to change 
                zones. This means that the card you draw will be the card you revealed.
                 */
                player.drawCards(1, source.getSourceId(), game);
            }
        } else {
            player.drawCards(1, source.getSourceId(), game);
        }
        return true;
    }
}
