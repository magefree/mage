
package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeAllControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.DragonToken2;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DayOfTheDragons extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragons");

    public DayOfTheDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");

        // When Day of the Dragons enters the battlefield, exile all creatures you control. Then create that many 5/5 red Dragon creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DayOfTheDragonsEntersEffect(), false));

        // When Day of the Dragons leaves the battlefield, sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new SacrificeAllControllerEffect(filter), false);
        ability.addEffect(new DayOfTheDragonsLeavesEffect());
        this.addAbility(ability);
    }

    private DayOfTheDragons(final DayOfTheDragons card) {
        super(card);
    }

    @Override
    public DayOfTheDragons copy() {
        return new DayOfTheDragons(this);
    }
}

class DayOfTheDragonsEntersEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public DayOfTheDragonsEntersEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creatures you control. Then create that many 5/5 red Dragon creature tokens with flying";
    }

    private DayOfTheDragonsEntersEffect(final DayOfTheDragonsEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Set<Card> toExile = new HashSet<>();
            toExile.addAll(game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game));
            if (!toExile.isEmpty()) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                controller.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());
                DragonToken2 token = new DragonToken2();
                token.putOntoBattlefield(toExile.size(), game, source, source.getControllerId());
            }
            return true;
        }
        return false;
    }

    @Override
    public DayOfTheDragonsEntersEffect copy() {
        return new DayOfTheDragonsEntersEffect(this);
    }
}

class DayOfTheDragonsLeavesEffect extends OneShotEffect {

    public DayOfTheDragonsLeavesEffect() {
        super(Outcome.Neutral);
        staticText = "Then return the exiled cards to the battlefield under your control";
    }

    private DayOfTheDragonsLeavesEffect(final DayOfTheDragonsLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null) {
            int zoneChangeCounter = source.getSourceObjectZoneChangeCounter();
            if (zoneChangeCounter > 0 && !(sourceObject instanceof PermanentToken)) {
                zoneChangeCounter--;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exile != null) {
                controller.moveCards(exile, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DayOfTheDragonsLeavesEffect copy() {
        return new DayOfTheDragonsLeavesEffect(this);
    }
}
