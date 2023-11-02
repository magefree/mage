package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FuriousRise extends CardImpl {

    public FuriousRise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your end step, if you control a creature with power 4 or greater, exile the top card of your library.
        // You may play that card until you exile another card with Furious Rise.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new FuriousRiseEffect(), TargetController.YOU, false), FerociousCondition.instance,
                "At the beginning of your end step, if you control a creature with power 4 or greater, exile the top card of your library. You may play that card until you exile another card with {this}."));
    }

    private FuriousRise(final FuriousRise card) {
        super(card);
    }

    @Override
    public FuriousRise copy() {
        return new FuriousRise(this);
    }
}

class FuriousRiseEffect extends OneShotEffect {

    public FuriousRiseEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. You may play that card until you exile another card with Furious Rise";
    }

    private FuriousRiseEffect(final FuriousRiseEffect effect) {
        super(effect);
    }

    @Override
    public FuriousRiseEffect copy() {
        return new FuriousRiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = source.getSourceObject(game);
        if (controller != null && mageObject != null) {
            Card cardToExile = controller.getLibrary().getFromTop(game);

            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            controller.moveCardsToExile(cardToExile, source, game, true, exileId, mageObject.getIdName() + " (" + source.getSourceObjectZoneChangeCounter() + ")");
            Card cardToPlay = game.getCard(cardToExile.getId());

            endPreviousEffect(game, source); // workaround for Furious Rise

            ContinuousEffect effect = new FuriousRisePlayEffect();
            effect.setTargetPointer(new FixedTarget(cardToPlay, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    private static boolean endPreviousEffect(Game game, Ability source) {
        for (AsThoughEffect effect : game.getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, game)) {
            if (effect instanceof FuriousRisePlayEffect) {
                for (Ability ability : game.getContinuousEffects().getAsThoughEffectsAbility(effect)) {
                    if (ability.getSourceId().equals(source.getSourceId())
                            && source.getSourceObjectZoneChangeCounter() == ability.getSourceObjectZoneChangeCounter()) {
                        effect.discard();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class FuriousRisePlayEffect extends AsThoughEffectImpl {

    public FuriousRisePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
    }

    private FuriousRisePlayEffect(final FuriousRisePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public FuriousRisePlayEffect copy() {
        return new FuriousRisePlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }
        return targets.contains(objectId)
                && affectedControllerId.equals(source.getControllerId());
    }
}
