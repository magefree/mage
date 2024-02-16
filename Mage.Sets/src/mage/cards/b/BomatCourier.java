
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class BomatCourier extends CardImpl {

    public BomatCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Bomat Courier attacks, exile the top card of your library face down.
        this.addAbility(new AttacksTriggeredAbility(new BomatCourierExileEffect(), false));

        // {R}, Discard your hand, Sacrifice Bomat Courier: Put all cards exiled with Bomat Courier into their owners' hands.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BomatCourierReturnEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new DiscardHandCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BomatCourier(final BomatCourier card) {
        super(card);
    }

    @Override
    public BomatCourier copy() {
        return new BomatCourier(this);
    }
}

class BomatCourierExileEffect extends OneShotEffect {

    BomatCourierExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top card of your library face down";
    }

    private BomatCourierExileEffect(final BomatCourierExileEffect effect) {
        super(effect);
    }

    @Override
    public BomatCourierExileEffect copy() {
        return new BomatCourierExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                card.setFaceDown(true, game);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                card.setFaceDown(true, game);
                return true;
            }
        }
        return false;
    }
}

class BomatCourierReturnEffect extends OneShotEffect {

    BomatCourierReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Put all cards exiled with {this} into their owners' hands";
    }

    private BomatCourierReturnEffect(final BomatCourierReturnEffect effect) {
        super(effect);
    }

    @Override
    public BomatCourierReturnEffect copy() {
        return new BomatCourierReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            if (exileZone != null) {
                controller.moveCards(exileZone, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
