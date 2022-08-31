package mage.cards.g;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class GodPharaohsGift extends CardImpl {

    public GodPharaohsGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // At the beginning of combat on your turn, you may exile a creature card from your graveyard. If you do, create a token that's a copy of that card, except it's a 4/4 black Zombie. It gains haste until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new GodPharaohsGiftEffect(), TargetController.YOU, false
        ));
    }

    private GodPharaohsGift(final GodPharaohsGift card) {
        super(card);
    }

    @Override
    public GodPharaohsGift copy() {
        return new GodPharaohsGift(this);
    }
}

class GodPharaohsGiftEffect extends OneShotEffect {

    GodPharaohsGiftEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may exile a creature card from your graveyard. " +
                "If you do, create a token that's a copy of that card, " +
                "except it's a 4/4 black Zombie. It gains haste until end of turn";
    }

    private GodPharaohsGiftEffect(final GodPharaohsGiftEffect effect) {
        super(effect);
    }

    @Override
    public GodPharaohsGiftEffect copy() {
        return new GodPharaohsGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, true
        );
        controller.choose(Outcome.PutCreatureInPlay, target, source, game);
        Card cardChosen = game.getCard(target.getFirstTarget());
        if (cardChosen == null || !controller.moveCards(cardChosen, Zone.EXILED, source, game)) {
            return false;
        }
        // create token and modify all attributes permanently (without game usage)
        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(cardChosen, game);
        token.removePTCDA();
        token.setPower(4);
        token.setToughness(4);
        token.getColor().setColor(ObjectColor.BLACK);
        token.removeAllCreatureTypes();
        token.addSubType(SubType.ZOMBIE);
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        List<Permanent> permanents = token
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!permanents.isEmpty()) {
            game.addEffect(new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        return true;
    }
}
