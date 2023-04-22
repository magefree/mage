package mage.cards.t;

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
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class TidehollowSculler extends CardImpl {

    public TidehollowSculler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tidehollow Sculler enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TidehollowScullerExileEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Tidehollow Sculler leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new TidehollowScullerLeaveEffect(), false));
    }

    private TidehollowSculler(final TidehollowSculler card) {
        super(card);
    }

    @Override
    public TidehollowSculler copy() {
        return new TidehollowSculler(this);
    }
}

class TidehollowScullerExileEffect extends OneShotEffect {

    public TidehollowScullerExileEffect() {
        super(Outcome.Exile);
        this.staticText = "target opponent reveals their hand and you choose a nonland card from it. Exile that card";
    }

    public TidehollowScullerExileEffect(final TidehollowScullerExileEffect effect) {
        super(effect);
    }

    @Override
    public TidehollowScullerExileEffect copy() {
        return new TidehollowScullerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        // 6/7/2013 	If Tidehollow Sculler leaves the battlefield before its first ability has resolved,
        //              its second ability will trigger. This ability will do nothing when it resolves.
        //              Then its first ability will resolve and exile the chosen card forever.
        if (controller != null
                && opponent != null) {
            opponent.revealCards("Tidehollow Sculler", opponent.getHand(), game);
            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card to exile"));
            if (controller.choose(Outcome.Exile, opponent.getHand(), target, source, game)) {
                Card card = opponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardsToExile(
                            card,
                            source,
                            game,
                            true,
                            CardUtil.getExileZoneId(game,
                                    source.getSourceId(),
                                    source.getSourceObjectZoneChangeCounter()),
                            "Tidehollow Sculler");
                }
            }
            return true;
        }
        return false;
    }
}

class TidehollowScullerLeaveEffect extends OneShotEffect {

    public TidehollowScullerLeaveEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled card to its owner's hand";
    }

    public TidehollowScullerLeaveEffect(final TidehollowScullerLeaveEffect effect) {
        super(effect);
    }

    @Override
    public TidehollowScullerLeaveEffect copy() {
        return new TidehollowScullerLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken)
                    ? source.getSourceObjectZoneChangeCounter()
                    : source.getSourceObjectZoneChangeCounter() - 1;
            ExileZone exZone = game.getExile().getExileZone(
                    CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exZone != null) {
                controller.moveCards(exZone, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
