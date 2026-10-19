package mage.util;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.function.Consumer;

/** Alchemy changes follow a physical card through zones, without becoming copiable values. */
public final class PerpetualUtil {

    private PerpetualUtil() {
    }

    public static void addAbility(Card card, Ability ability, Ability source, Game game) {
        CardUtil.getObjectPartsAsObjects(card).stream().filter(Card.class::isInstance).map(Card.class::cast)
                .forEach(part -> game.addEffect(new PerpetualGainAbilityEffect(ability)
                        .setTargetPointer(new FixedTarget(part.getId(), 0)), source));
    }

    public static void modify(Card card, Game game, Consumer<Card> change) {
        Card main = game.getCard(card.getMainCard().getId());
        if (game.getState().getPerpetualOriginalCard(main.getId()) == null) {
            game.getState().setPerpetualOriginalCard(main.copy());
        }
        Card changed = main.copy();
        change.accept(changed);
        game.getState().setPerpetualCard(changed);
    }

    public static void boost(Card card, int power, int toughness, Ability source, Game game) {
        Card main = card.getMainCard() == null ? card : card.getMainCard();
        if (game.getCard(main.getId()) != null) {
            modify(main, game, changed -> CardUtil.getObjectPartsAsObjects(changed).stream()
                    .filter(Card.class::isInstance).map(Card.class::cast).filter(part -> part.isCreature(game))
                    .forEach(part -> ((CardImpl) part).setBasePowerToughness(
                            part.getPower().getBaseValue() + power, part.getToughness().getBaseValue() + toughness)));
            game.getState().setValue("perpetualPower_" + main.getId(), getBoost(main, "Power", game) + power);
            game.getState().setValue("perpetualToughness_" + main.getId(), getBoost(main, "Toughness", game) + toughness);
        }
        CardUtil.getObjectPartsAsObjects(card).stream().filter(Card.class::isInstance).map(Card.class::cast)
                .forEach(part -> game.addEffect(new BoostTargetEffect(power, toughness, Duration.EndOfGame)
                        .setTargetPointer(new FixedTarget(part.getId(), 0)), source));
    }

    public static void specialize(Card original, Card form, Game game) {
        CardImpl printed = (CardImpl) form.copy();
        printed.assignId(original.getId());
        printed.setOwnerId(original.getOwnerId());
        printed.getAbilities().setControllerId(printed.getOwnerId());
        game.getState().setPerpetualOriginalCard(printed);
        CardImpl changed = (CardImpl) printed.copy();
        changed.setBasePowerToughness(form.getPower().getBaseValue() + getBoost(original, "Power", game),
                form.getToughness().getBaseValue() + getBoost(original, "Toughness", game));
        game.getState().setPerpetualCard(changed);
    }

    private static int getBoost(Card card, String characteristic, Game game) {
        Integer boost = (Integer) game.getState().getValue("perpetual" + characteristic + "_" + card.getMainCard().getId());
        return boost == null ? 0 : boost;
    }
}

class PerpetualGainAbilityEffect extends GainAbilityTargetEffect {

    PerpetualGainAbilityEffect(Ability ability) {
        super(ability, Duration.EndOfGame, null, true);
        setAffectedObjectsSet(false);
    }

    private PerpetualGainAbilityEffect(final PerpetualGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public PerpetualGainAbilityEffect copy() {
        return new PerpetualGainAbilityEffect(this);
    }
}
