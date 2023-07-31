package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

/**
 * @author jeffwadsworth
 */
public final class ChandraPyromaster extends CardImpl {

    public ChandraPyromaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(4);

        // +1: Chandra, Pyromaster deals 1 damage to target player and 1 damage to 
        // up to one target creature that player controls. That creature can't block this turn.
        LoyaltyAbility ability1 = new LoyaltyAbility(new ChandraPyromasterEffect1(), 1);
        Target target1 = new TargetPlayerOrPlaneswalker();
        ability1.addTarget(target1);
        ability1.addTarget(new ChandraPyromasterTarget());
        this.addAbility(ability1);

        // 0: Exile the top card of your library. You may play it this turn.
        LoyaltyAbility ability2 = new LoyaltyAbility(new ChandraPyromasterEffect2(), 0);
        this.addAbility(ability2);

        // -7: Exile the top ten cards of your library. Choose an instant or sorcery 
        // card exiled this way and copy it three times. You may cast the copies 
        // without paying their mana costs.
        LoyaltyAbility ability3 = new LoyaltyAbility(new ChandraPyromasterEffect3(), -7);
        this.addAbility(ability3);

    }

    private ChandraPyromaster(final ChandraPyromaster card) {
        super(card);
    }

    @Override
    public ChandraPyromaster copy() {
        return new ChandraPyromaster(this);
    }
}

class ChandraPyromasterEffect1 extends OneShotEffect {

    public ChandraPyromasterEffect1() {
        super(Outcome.Damage);
        staticText = "{this} deals 1 damage to target player or planeswalker "
                + "and 1 damage to up to one target creature that player or that "
                + "planeswalker's controller controls. That creature can't block this turn.";
    }

    public ChandraPyromasterEffect1(final ChandraPyromasterEffect1 effect) {
        super(effect);
    }

    @Override
    public ChandraPyromasterEffect1 copy() {
        return new ChandraPyromasterEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.damagePlayerOrPermanent(source.getTargets().get(0).getFirstTarget(),
                1, source.getSourceId(), source, game, false, true);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.damage(1, source.getSourceId(), source, game, false, true);
            ContinuousEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class ChandraPyromasterTarget extends TargetPermanent {

    public ChandraPyromasterTarget() {
        super(0, 1, new FilterCreaturePermanent("creature that the targeted player "
                + "or planeswalker's controller controls"), false);
    }

    public ChandraPyromasterTarget(final ChandraPyromasterTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        UUID firstTarget = player.getId();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.isControlledBy(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(source);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(source.getSourceId())) {
                object = item;
            }
            if (item.getSourceId().equals(source.getSourceId())) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            Player player = game.getPlayerOrPlaneswalkerController(playerId);
            if (player != null) {
                for (UUID targetId : availablePossibleTargets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.isControlledBy(player.getId())) {
                        possibleTargets.add(targetId);
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public ChandraPyromasterTarget copy() {
        return new ChandraPyromasterTarget(this);
    }
}

class ChandraPyromasterEffect2 extends OneShotEffect {

    public ChandraPyromasterEffect2() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. You may play it this turn";
    }

    public ChandraPyromasterEffect2(final ChandraPyromasterEffect2 effect) {
        super(effect);
    }

    @Override
    public ChandraPyromasterEffect2 copy() {
        return new ChandraPyromasterEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(
                        Zone.EXILED, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class ChandraPyromasterEffect3 extends OneShotEffect {

    public ChandraPyromasterEffect3() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top ten cards of your library. Choose an instant "
                + "or sorcery card exiled this way and copy it three times. "
                + "You may cast the copies without paying their mana costs";
    }

    public ChandraPyromasterEffect3(final ChandraPyromasterEffect3 effect) {
        super(effect);
    }

    @Override
    public ChandraPyromasterEffect3 copy() {
        return new ChandraPyromasterEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 10));
        controller.moveCardsToExile(cards.getCards(game), source, game, true, source.getSourceId(), sourceObject.getIdName());

        if (!cards.getCards(new FilterInstantOrSorceryCard(), game).isEmpty()) {
            TargetCard target = new TargetCard(Zone.EXILED, new FilterInstantOrSorceryCard());
            if (controller.chooseTarget(Outcome.PlayForFree, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    ApprovingObject approvingObject = new ApprovingObject(source, game);
                    if (controller.chooseUse(outcome, "Cast copy 1 of " + card.getName(), source, game)) {
                        Card copy1 = game.copyCard(card, source, source.getControllerId());
                        game.getState().setValue("PlayFromNotOwnHandZone" + copy1.getId(), Boolean.TRUE);
                        controller.cast(controller.chooseAbilityForCast(copy1, game, true),
                                game, true, approvingObject);
                        game.getState().setValue("PlayFromNotOwnHandZone" + copy1.getId(), null);
                    }
                    if (controller.chooseUse(outcome, "Cast copy 2 of " + card.getName(), source, game)) {
                        Card copy2 = game.copyCard(card, source, source.getControllerId());
                        game.getState().setValue("PlayFromNotOwnHandZone" + copy2.getId(), Boolean.TRUE);
                        controller.cast(controller.chooseAbilityForCast(copy2, game, true),
                                game, true, approvingObject);
                        game.getState().setValue("PlayFromNotOwnHandZone" + copy2.getId(), null);
                    }
                    if (controller.chooseUse(outcome, "Cast copy 3 of " + card.getName(), source, game)) {
                        Card copy3 = game.copyCard(card, source, source.getControllerId());
                        game.getState().setValue("PlayFromNotOwnHandZone" + copy3.getId(), Boolean.TRUE);
                        controller.cast(controller.chooseAbilityForCast(copy3, game, true),
                                game, true, approvingObject);
                        game.getState().setValue("PlayFromNotOwnHandZone" + copy3.getId(), null);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
