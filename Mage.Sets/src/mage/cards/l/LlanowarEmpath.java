
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 *
 */
public final class LlanowarEmpath extends CardImpl {

    public LlanowarEmpath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Llanowar Empath enters the battlefield, scry 2, then reveal the top card of your library. If it's a creature card, put it into your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(2, false));
        ability.addEffect(new LlanowarEmpathEffect());
        this.addAbility(ability);
    }

    private LlanowarEmpath(final LlanowarEmpath card) {
        super(card);
    }

    @Override
    public LlanowarEmpath copy() {
        return new LlanowarEmpath(this);
    }
}

class LlanowarEmpathEffect extends OneShotEffect {

    public LlanowarEmpathEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then reveal the top card of your library. If it's a creature card, put it into your hand.";
    }

    public LlanowarEmpathEffect(final LlanowarEmpathEffect effect) {
        super(effect);
    }

    @Override
    public LlanowarEmpathEffect copy() {
        return new LlanowarEmpathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            controller.revealCards(sourceObject.getName(), cards, game);
            if (card.isCreature(game)) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
