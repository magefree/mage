
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Saga
 */
public final class HeirloomBlade extends CardImpl {

    public HeirloomBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 1)));

        // Whenever equipped creature dies, you may reveal cards from the top of your library until you reveal a creature card that shares a creature type with it.
        // Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new DiesAttachedTriggeredAbility(new HeirloomBladeEffect(), "equipped creature", true));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), false));

    }

    private HeirloomBlade(final HeirloomBlade card) {
        super(card);
    }

    @Override
    public HeirloomBlade copy() {
        return new HeirloomBlade(this);
    }
}

class HeirloomBladeEffect extends OneShotEffect {

    public HeirloomBladeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal cards from the top of your library until you reveal a creature card that shares a creature type with it. "
                + "Put that card into your hand and the rest on the bottom of your library in a random order";
    }

    public HeirloomBladeEffect(final HeirloomBladeEffect effect) {
        super(effect);
    }

    @Override
    public HeirloomBladeEffect copy() {
        return new HeirloomBladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && equipment != null) {
            Permanent equipped = (Permanent) getValue("attachedTo");
            if (equipped != null) {
                Cards revealed = new CardsImpl();
                Cards otherCards = new CardsImpl();
                for (Card card : controller.getLibrary().getCards(game)) {
                    revealed.add(card);
                    if (card != null && card.isCreature(game) && equipped.shareCreatureTypes(game, card)) {
                        controller.moveCardToHandWithInfo(card, source, game, true);
                        break;
                    } else {
                        otherCards.add(card);
                    }
                }
                controller.revealCards(equipment.getIdName(), revealed, game);
                controller.putCardsOnBottomOfLibrary(otherCards, game, source, false);
                return true;
            }
        }
        return false;
    }
}
