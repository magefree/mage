
package mage.cards.w;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class WorldgorgerDragon extends CardImpl {

    public WorldgorgerDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}{R}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Worldgorger Dragon enters the battlefield, exile all other permanents you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WorldgorgerDragonEntersEffect(), false));

        // When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new WorldgorgerDragonLeavesEffect(), false));
    }

    private WorldgorgerDragon(final WorldgorgerDragon card) {
        super(card);
    }

    @Override
    public WorldgorgerDragon copy() {
        return new WorldgorgerDragon(this);
    }
}

class WorldgorgerDragonEntersEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all other permanents you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public WorldgorgerDragonEntersEffect() {
        super(Outcome.Detriment);
        staticText = "exile all other permanents you control";
    }

    public WorldgorgerDragonEntersEffect(final WorldgorgerDragonEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileId != null) {
                Set<Card> cardsToExile = new LinkedHashSet<>();
                cardsToExile.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
                controller.moveCardsToExile(cardsToExile, source, game, true, exileId, sourceObject.getIdName());
                return true;
            }
        }
        return false;
    }

    @Override
    public WorldgorgerDragonEntersEffect copy() {
        return new WorldgorgerDragonEntersEffect(this);
    }
}

class WorldgorgerDragonLeavesEffect extends OneShotEffect {

    public WorldgorgerDragonLeavesEffect() {
        super(Outcome.Neutral);
        staticText = "return the exiled cards to the battlefield under their owners' control";
    }

    public WorldgorgerDragonLeavesEffect(final WorldgorgerDragonLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() - 1;
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exile != null) {
                return controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return false;

    }

    @Override
    public WorldgorgerDragonLeavesEffect copy() {
        return new WorldgorgerDragonLeavesEffect(this);
    }
}
