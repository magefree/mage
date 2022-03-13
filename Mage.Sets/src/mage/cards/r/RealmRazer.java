
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class RealmRazer extends CardImpl {

    public RealmRazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Realm Razer enters the battlefield, exile all lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileAllEffect()));
        // When Realm Razer leaves the battlefield, return the exiled cards to the battlefield tapped under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RealmRazerEffect(), false));
    }

    private RealmRazer(final RealmRazer card) {
        super(card);
    }

    @Override
    public RealmRazer copy() {
        return new RealmRazer(this);
    }
}

class ExileAllEffect extends OneShotEffect {

    public ExileAllEffect() {
        super(Outcome.Exile);
        staticText = "exile all lands";
    }

    public ExileAllEffect(final ExileAllEffect effect) {
        super(effect);
    }

    @Override
    public ExileAllEffect copy() {
        return new ExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            permanent.moveToExile(source.getSourceId(), "Realm Razer", source, game);
        }
        return true;
    }

}

class RealmRazerEffect extends OneShotEffect {

    public RealmRazerEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled cards to the battlefield tapped under their owners' control";
    }

    public RealmRazerEffect(final RealmRazerEffect effect) {
        super(effect);
    }

    @Override
    public RealmRazerEffect copy() {
        return new RealmRazerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exZone = game.getExile().getExileZone(source.getSourceId());
            if (exZone != null) {
                return controller.moveCards(exZone.getCards(game), Zone.BATTLEFIELD, source, game, true, false, true, null);
            }
            return true;
        }
        return false;
    }
}
