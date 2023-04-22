package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowKin extends CardImpl {

    public ShadowKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // At the beginning of your upkeep, each player mills three cards. You may exile a creature card from among the cards milled this way. If you do, Shadow Kin becomes a copy of that card, except it has this ability.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ShadowKinEffect(), TargetController.YOU, false
        ));
    }

    private ShadowKin(final ShadowKin card) {
        super(card);
    }

    @Override
    public ShadowKin copy() {
        return new ShadowKin(this);
    }
}

class ShadowKinEffect extends OneShotEffect {

    ShadowKinEffect() {
        super(Outcome.Benefit);
        staticText = "each player mills three cards. You may exile a creature card from among " +
                "the cards milled this way. If you do, {this} becomes a copy of that card, except it has this ability";
    }

    private ShadowKinEffect(final ShadowKinEffect effect) {
        super(effect);
    }

    @Override
    public ShadowKinEffect copy() {
        return new ShadowKinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.addAll(player.millCards(3, source, game));
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        TargetCardInGraveyard target = new TargetCardInGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        );
        target.setNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (card == null || sourcePermanent == null) {
            return true;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        Permanent blueprint = new PermanentCard(card, source.getControllerId(), game);
        blueprint.assignNewId();
        CopyApplier applier = new ShadowKinApplier();
        applier.apply(game, blueprint, source, sourcePermanent.getId());
        CopyEffect copyEffect = new CopyEffect(Duration.Custom, blueprint, sourcePermanent.getId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, newAbility);
        return true;
    }
}

class ShadowKinApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(
                new ShadowKinEffect(), TargetController.YOU, false
        ));
        return true;
    }
}