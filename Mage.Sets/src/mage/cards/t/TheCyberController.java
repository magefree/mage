package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheCyberController extends CardImpl {

    public TheCyberController(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}{U}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CYBERMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When The Cyber-Controller enters the battlefield, each opponent mills X cards. Put all creature cards milled this way onto the battlefield face down under your control. They're 2/2 Cyberman artifact creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheCyberControllerEffect()));

        // Other artifact creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        )));
    }

    private TheCyberController(final TheCyberController card) {
        super(card);
    }

    @Override
    public TheCyberController copy() {
        return new TheCyberController(this);
    }
}

class TheCyberControllerEffect extends OneShotEffect {

    TheCyberControllerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "each opponent mills X cards. Put all creature cards milled this way onto the battlefield " +
                "face down under your control. They're 2/2 Cyberman artifact creatures";
    }

    private TheCyberControllerEffect(final TheCyberControllerEffect effect) {
        super(effect);
    }

    @Override
    public TheCyberControllerEffect copy() {
        return new TheCyberControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cardsToMove = new CardsImpl();
        int amount = CardUtil.getSourceCostsTag(game, source, "X", 0);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!controller.hasOpponent(playerId, game)) {
                continue;
            }
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            opponent
                    .millCards(amount, source, game)
                    .getCards(game)
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD)
                    .filter(card -> card.isCreature(game))
                    .forEach(cardsToMove::add);
        }
        if (cardsToMove.isEmpty()) {
            return true;
        }
        game.addEffect(new TheCyberControllerFaceDownEffect().setTargetPointer(new FixedTargets(
                cardsToMove
                        .getCards(game)
                        .stream()
                        .map(card -> new MageObjectReference(card, game, 1))
                        .collect(Collectors.toList())
        )), source);
        controller.moveCards(
                cardsToMove.getCards(game), Zone.BATTLEFIELD, source, game,
                false, true, false, null
        );
        return true;
    }
}

class TheCyberControllerFaceDownEffect extends ContinuousEffectImpl {

    TheCyberControllerFaceDownEffect() {
        super(Duration.Custom, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.Neutral);
    }

    private TheCyberControllerFaceDownEffect(final TheCyberControllerFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public TheCyberControllerFaceDownEffect copy() {
        return new TheCyberControllerFaceDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean found = false;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null || !permanent.isFaceDown(game)) {
                continue;
            }
            found = true;
            BecomesFaceDownCreatureEffect.makeFaceDownObject(
                    game,
                    source.getSourceId(),
                    permanent,
                    BecomesFaceDownCreatureEffect.FaceDownType.MANUAL,
                    null
            );
            permanent.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
            permanent.addSubType(game, SubType.CYBERMAN);
            permanent.getPower().setModifiedBaseValue(2);
            permanent.getToughness().setModifiedBaseValue(2);
        }
        if (!found) {
            discard();
            return false;
        }
        return true;
    }
}
