package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AuguryAdept extends CardImpl {

    public AuguryAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Augury Adept deals combat damage to a player, reveal the top card of your library and put that card into your hand. You gain life equal to its converted mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AuguryAdeptEffect(), false));
    }

    private AuguryAdept(final AuguryAdept card) {
        super(card);
    }

    @Override
    public AuguryAdept copy() {
        return new AuguryAdept(this);
    }
}

class AuguryAdeptEffect extends OneShotEffect {

    AuguryAdeptEffect() {
        super(Outcome.GainLife);
        this.staticText = "reveal the top card of your library and put that card into your hand. " +
                "You gain life equal to its mana value";
    }

    private AuguryAdeptEffect(final AuguryAdeptEffect effect) {
        super(effect);
    }

    @Override
    public AuguryAdeptEffect copy() {
        return new AuguryAdeptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        controller.moveCards(card, Zone.HAND, source, game);
        int cmc = card.getManaValue();
        if (cmc > 0) {
            controller.gainLife(cmc, game, source);
        }
        return true;
    }
}
