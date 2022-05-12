package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DetentionSphere extends CardImpl {

    static final protected FilterPermanent filter = new FilterNonlandPermanent("nonland permanent not named Detention Sphere");

    static {
        filter.add(Predicates.not(new NamePredicate("Detention Sphere")));
    }

    public DetentionSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        // When Detention Sphere enters the battlefield, you may exile
        // target nonland permanent not named Detention Sphere and all
        // other permanents with the same name as that permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetentionSphereEntersEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Detention Sphere leaves the battlefield, return the exiled
        // cards to the battlefield under their owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DetentionSphereLeavesEffect(), false));
    }

    private DetentionSphere(final DetentionSphere card) {
        super(card);
    }

    @Override
    public DetentionSphere copy() {
        return new DetentionSphere(this);
    }
}

class DetentionSphereEntersEffect extends OneShotEffect {

    public DetentionSphereEntersEffect() {
        super(Outcome.Exile);
        staticText = "you may exile target nonland permanent not named Detention Sphere and all other permanents with the same name as that permanent";
    }

    public DetentionSphereEntersEffect(final DetentionSphereEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && exileId != null && targetPermanent != null && controller != null) {

            if (CardUtil.haveEmptyName(targetPermanent)) { // face down creature
                controller.moveCardToExileWithInfo(targetPermanent, exileId, sourceObject.getIdName(), source, game, Zone.BATTLEFIELD, true);
            } else {
                String name = targetPermanent.getName();
                for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (permanent != null && CardUtil.haveSameNames(permanent, name, game)) {
                        controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getIdName(), source, game, Zone.BATTLEFIELD, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DetentionSphereEntersEffect copy() {
        return new DetentionSphereEntersEffect(this);
    }
}

class DetentionSphereLeavesEffect extends OneShotEffect {

    public DetentionSphereLeavesEffect() {
        super(Outcome.Neutral);
        staticText = "return the exiled cards to the battlefield under their owner's control";
    }

    public DetentionSphereLeavesEffect(final DetentionSphereLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
            if (permanentLeftBattlefield == null) {
                Logger.getLogger(ReturnFromExileForSourceEffect.class).error("Permanent not found: " + sourceObject.getName());
                return false;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game)));
            if (exile != null) {
                controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }

    @Override
    public DetentionSphereLeavesEffect copy() {
        return new DetentionSphereLeavesEffect(this);
    }
}
