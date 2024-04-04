package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.YouMaySpendManaAsAnyColorToCastTargetEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.constants.CastManaAdjustment;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron, Susucr
 */
public class MayCastTargetCardEffect extends OneShotEffect {

    private final Duration duration;

    private final CastManaAdjustment manaAdjustment;

    private final boolean thenExile; // Should the spell be exiled by a replacement effect if cast and it resolves?

    /**
     * Allows to cast the target card immediately, for its manacost.
     */
    public MayCastTargetCardEffect(boolean exileOnResolve) {
        this(CastManaAdjustment.NONE, exileOnResolve);
    }

    /**
     * Allows to cast the target card immediately, either for its cost or with a modifier (like for free, or mana as any type).
     */
    public MayCastTargetCardEffect(CastManaAdjustment manaAdjustment, boolean exileOnResolve) {
        this(Duration.OneUse, manaAdjustment, exileOnResolve);
    }

    /**
     * Makes the target card playable for the specified duration as long as it remains in that zone.
     */
    public MayCastTargetCardEffect(Duration duration, boolean exileOnResolve) {
        this(duration, CastManaAdjustment.NONE, exileOnResolve);
    }

    protected MayCastTargetCardEffect(Duration duration, CastManaAdjustment manaAdjustment, boolean thenExile) {
        super(Outcome.Benefit);
        this.duration = duration;
        this.manaAdjustment = manaAdjustment;
        this.thenExile = thenExile;

        // TODO: support the non-yet-supported combinations.
        //       for now the constructor chains won't allow those.
        if (duration != Duration.OneUse && manaAdjustment != CastManaAdjustment.NONE) {
            throw new IllegalStateException(
                    "Wrong code usage, not yet supported "
                            + "duration={" + duration.name() + "}, "
                            + "manaAdjustment={" + manaAdjustment.name() + "}"
            );
        }
    }

    protected MayCastTargetCardEffect(final MayCastTargetCardEffect effect) {
        super(effect);
        this.duration = effect.duration;
        this.manaAdjustment = effect.manaAdjustment;
        this.thenExile = effect.thenExile;
    }

    @Override
    public MayCastTargetCardEffect copy() {
        return new MayCastTargetCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        if (duration == Duration.OneUse) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null || !controller.chooseUse(outcome, "Cast " + card.getLogName() + '?', source, game)) {
                return false;
            }

            switch (manaAdjustment) {
                case NONE:
                case WITHOUT_PAYING_MANA_COST:
                    break;
                case AS_THOUGH_ANY_MANA_COLOR:
                case AS_THOUGH_ANY_MANA_TYPE:
                    // TODO: untangle why there is a confusion between the two.
                    ContinuousEffect effect =
                            new YouMaySpendManaAsAnyColorToCastTargetEffect(Duration.Custom, controller.getId(), null);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong code usage, manaAdjustment is not yet supported: " + manaAdjustment);
            }

            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            boolean noMana = manaAdjustment == CastManaAdjustment.WITHOUT_PAYING_MANA_COST;
            controller.cast(controller.chooseAbilityForCast(card, game, noMana),
                    game, noMana, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        } else {
            // TODO: support (and add tests!) for the non-NONE manaAdjustment
            CardUtil.makeCardPlayable(game, source, card, duration, false);
        }
        if (thenExile) {
            ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(true);
            effect.setTargetPointer(new FixedTarget(card, game));
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
            default:
                throw new IllegalArgumentException("Wrong code usage, manaAdjustment is not yet supported: " + manaAdjustment);
        }
        text += ".";
        if (thenExile) {
            text += " " + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
        }
        return text;
    }

}
