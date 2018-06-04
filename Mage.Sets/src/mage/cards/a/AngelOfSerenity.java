
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class AngelOfSerenity extends CardImpl {

    public AngelOfSerenity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        this.addAbility(new AngelOfSerenityTriggeredAbility());

        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.HAND, false, true), false));
    }

    public AngelOfSerenity(final AngelOfSerenity card) {
        super(card);
    }

    @Override
    public AngelOfSerenity copy() {
        return new AngelOfSerenity(this);
    }
}

class AngelOfSerenityTriggeredAbility extends ZoneChangeTriggeredAbility {

    public AngelOfSerenityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AngelOfSerenityEnterEffect(), "When {this} enters the battlefield, ", true);
    }

    public AngelOfSerenityTriggeredAbility(AngelOfSerenityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("up to three other target creatures");
            filter.add(new AnotherPredicate());
            TargetCreaturePermanent target1 = new TargetCreaturePermanent(0, 3, filter, false);
            game.getPlayer(getControllerId()).chooseTarget(Outcome.Exile, target1, this, game);
            if (!target1.getTargets().isEmpty()) {
                getTargets().add(target1);

            }
            int leftTargets = 3 - target1.getTargets().size();
            if (leftTargets > 0) {
                FilterCard filter2 = new FilterCreatureCard("up to " + leftTargets + " target creature card" + (leftTargets > 1 ? "s" : "") + " from graveyards");
                TargetCardInGraveyard target2 = new TargetCardInGraveyard(0, leftTargets, filter2);
                game.getPlayer(getControllerId()).chooseTarget(Outcome.Exile, target2, this, game);
                if (!target2.getTargets().isEmpty()) {
                    getTargets().add(target2);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AngelOfSerenityTriggeredAbility copy() {
        return new AngelOfSerenityTriggeredAbility(this);
    }

}

class AngelOfSerenityEnterEffect extends OneShotEffect {

    public AngelOfSerenityEnterEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards";
    }

    public AngelOfSerenityEnterEffect(final AngelOfSerenityEnterEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfSerenityEnterEffect copy() {
        return new AngelOfSerenityEnterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true;
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null && !source.getTargets().isEmpty()) {
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            for (Target target : source.getTargets()) {
                if (target instanceof TargetCreaturePermanent) {
                    for (UUID permanentId : target.getTargets()) {
                        Permanent permanent = game.getPermanent(permanentId);
                        if (permanent != null) {
                            result |= controller.moveCardToExileWithInfo(permanent, exileZoneId, sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
                        }
                    }

                } else if (target instanceof TargetCardInGraveyard) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            result |= controller.moveCardToExileWithInfo(card, exileZoneId, sourceObject.getIdName(), source.getSourceId(), game, Zone.GRAVEYARD, true);
                        }
                    }
                }
            }
        }
        return result;
    }
}
