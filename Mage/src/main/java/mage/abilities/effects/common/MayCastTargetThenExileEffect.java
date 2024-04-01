package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.YouMaySpendManaAsAnyColorToCastTargetEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron, Susucr
 */
public class MayCastTargetThenExileEffect extends OneShotEffect {

    private final Duration duration;

    private final CardUtil.CastManaAdjustment manaAdjustment;

    // Set to true with `withNoExile` if not wanting the exile clause (rare).
    private boolean noExile;

    /**
     * Allows to cast the target card immediately, for its manacost.
     * If resulting spell would be put into graveyard, exiles it instead.
     */
    public MayCastTargetThenExileEffect() {
        this(CardUtil.CastManaAdjustment.NONE);
    }

    /**
     * Allows to cast the target card immediately, either for its cost or with a modifier (like for free, or mana as any type).
     * If resulting spell would be put into graveyard, exiles it instead.
     */
    public MayCastTargetThenExileEffect(CardUtil.CastManaAdjustment manaAdjustment) {
        this(Duration.OneUse, manaAdjustment);
    }

    /**
     * Makes the target card playable for the specified duration as long as it remains in that zone.
     * If resulting spell would be put into graveyard, exiles it instead.
     */
    public MayCastTargetThenExileEffect(Duration duration) {
        this(duration, CardUtil.CastManaAdjustment.NONE);
    }

    protected MayCastTargetThenExileEffect(Duration duration, CardUtil.CastManaAdjustment manaAdjustment) {
        super(Outcome.Benefit);
        this.duration = duration;
        this.manaAdjustment = manaAdjustment;
        this.noExile = false;

        // TODO: support the non-yet-supported combinations.
        //       for now the constructor chains won't allow those.
        if (duration != Duration.OneUse && manaAdjustment != CardUtil.CastManaAdjustment.NONE) {
            throw new IllegalStateException(
                    "Not yet supported MayCastTargetThenExileEffect combination "
                            + "duration={" + duration.name() + "}, "
                            + "manaAdjustment={" + manaAdjustment.name() + "}"
            );
        }
    }

    protected MayCastTargetThenExileEffect(final MayCastTargetThenExileEffect effect) {
        super(effect);
        this.duration = effect.duration;
        this.manaAdjustment = effect.manaAdjustment;
        this.noExile = effect.noExile;
    }

    @Override
    public MayCastTargetThenExileEffect copy() {
        return new MayCastTargetThenExileEffect(this);
    }

    public MayCastTargetThenExileEffect withNoExile(boolean noExile) {
        this.noExile = noExile;
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        FixedTarget fixedTarget = new FixedTarget(card, game);
        if (duration == Duration.OneUse) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null || !controller.chooseUse(outcome, "Cast " + card.getLogName() + '?', source, game)) {
                return false;
            }

            ContinuousEffect shortlivedManaReplacementEffect = null;
            switch (manaAdjustment) {
                case NONE:
                case WITHOUT_PAYING_MANA_COST:
                    break;
                case AS_THOUGH_ANY_MANA_COLOR:
                case AS_THOUGH_ANY_MANA_TYPE:
                    // TODO: untangle why there is a confusion between the two.
                    shortlivedManaReplacementEffect =
                            new YouMaySpendManaAsAnyColorToCastTargetEffect(Duration.Custom, controller.getId(), null)
                                    .setTargetPointer(fixedTarget);
                    game.addEffect(shortlivedManaReplacementEffect, source);
            }

            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            boolean noMana = manaAdjustment == CardUtil.CastManaAdjustment.WITHOUT_PAYING_MANA_COST;
            controller.cast(controller.chooseAbilityForCast(card, game, noMana),
                    game, noMana, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);

            if (shortlivedManaReplacementEffect != null) {
                shortlivedManaReplacementEffect.discard();
            }
        } else {
            // TODO: support (and add tests!) for the non-NONE manaAdjustment
            CardUtil.makeCardPlayable(game, source, card, duration, false);
        }
        if (!noExile) {
            ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(true);
            effect.setTargetPointer(fixedTarget);
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String text = "you may cast " + getTargetPointer().describeTargets(mode.getTargets(), "it");
        if (duration == Duration.EndOfTurn) {
            text += " this turn";
        } else if (!duration.toString().isEmpty()) {
            text += duration.toString();
        }
        switch (manaAdjustment) {
            case NONE:
                break;
            case WITHOUT_PAYING_MANA_COST:
                text += " without paying its mana cost";
                break;
            case AS_THOUGH_ANY_MANA_COLOR:
                text += ", and mana of any color can be spent to cast that spell";
                break;
            case AS_THOUGH_ANY_MANA_TYPE:
                text += ", and mana of any type can be spent to cast that spell";
                break;
        }
        text += ".";
        if (!noExile) {
            text += " " + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
        }
        return text;
    }

}
