
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class MesmericFiend extends CardImpl {

    public MesmericFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Mesmeric Fiend enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MesmericFiendExileEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Mesmeric Fiend leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new MesmericFiendLeaveEffect(), false));
    }

    public MesmericFiend(final MesmericFiend card) {
        super(card);
    }

    @Override
    public MesmericFiend copy() {
        return new MesmericFiend(this);
    }
}

class MesmericFiendExileEffect extends OneShotEffect {

    public MesmericFiendExileEffect() {
        super(Outcome.Exile);
        this.staticText = "target opponent reveals their hand and you choose a nonland card from it. Exile that card";
    }

    public MesmericFiendExileEffect(final MesmericFiendExileEffect effect) {
        super(effect);
    }

    @Override
    public MesmericFiendExileEffect copy() {
        return new MesmericFiendExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        Permanent sourcePermanent = (Permanent) source.getSourceObject(game);
        if (controller != null && opponent != null && sourcePermanent != null) {
            opponent.revealCards(sourcePermanent.getName(), opponent.getHand(), game);

            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card to exile"));
            if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                Card card = opponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.HAND, true);
                }
            }

            return true;
        }
        return false;
    }

}

class MesmericFiendLeaveEffect extends OneShotEffect {

    public MesmericFiendLeaveEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled card to its owner's hand";
    }

    public MesmericFiendLeaveEffect(final MesmericFiendLeaveEffect effect) {
        super(effect);
    }

    @Override
    public MesmericFiendLeaveEffect copy() {
        return new MesmericFiendLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() - 1;
            ExileZone exZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exZone != null) {
                return controller.moveCards(exZone, Zone.HAND, source, game);
            }
        }
        return false;
    }
}
