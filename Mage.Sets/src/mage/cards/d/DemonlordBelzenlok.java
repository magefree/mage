package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonlordBelzenlok extends CardImpl {

    public DemonlordBelzenlok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Demonlord Belzenlok enters the battlefield, exile cards from the top of your library until you exile a nonland card, then put that card into your hand. If the card's converted mana cost is 4 or greater, repeat this process. Demonlord Belzenlok deals 1 damage to you for each card put into your hand this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DemonlordBelzenlokEffect()));
    }

    private DemonlordBelzenlok(final DemonlordBelzenlok card) {
        super(card);
    }

    @Override
    public DemonlordBelzenlok copy() {
        return new DemonlordBelzenlok(this);
    }
}

class DemonlordBelzenlokEffect extends OneShotEffect {

    public DemonlordBelzenlokEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card, then put that card into your hand. "
                + "If the card's mana value is 4 or greater, repeat this process. "
                + "{this} deals 1 damage to you for each card put into your hand this way";
    }

    public DemonlordBelzenlokEffect(final DemonlordBelzenlokEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        boolean cont = true;
        int addedToHand = 0;
        while (controller.getLibrary().hasCards() && cont) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                if (!card.isLand(game)) {
                    if (card.getManaValue() < 4) {
                        cont = false;
                    }
                    controller.moveCards(card, Zone.HAND, source, game);
                    addedToHand++;
                }
            }
        }
        controller.damage(addedToHand, source.getSourceId(), source, game);
        return true;
    }

    @Override
    public DemonlordBelzenlokEffect copy() {
        return new DemonlordBelzenlokEffect(this);
    }
}
