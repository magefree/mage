package mage.abilities.effects.common;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.YouMaySpendManaAsAnyColorToCastTargetEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron, Susucr
 */
public class MayCastTargetCardEffect extends OneShotEffect {

    private final Duration duration;

    private final CastManaAdjustment manaAdjustment;

    private final boolean thenExile; // Should the spell be exiled by a replacement effect if cast and it resolves?
    private final TargetController targetController; // Who is allwoed to cast the card.

    /**
     * Allows to cast the target card immediately, for its manacost.
     */
    public MayCastTargetCardEffect(boolean thenExile) {
        this(CastManaAdjustment.NONE, thenExile);
    }

    /**
     * Allows to cast the target card immediately, either for its cost or with a modifier (like for free, or mana as any type).
     */
    public MayCastTargetCardEffect(CastManaAdjustment manaAdjustment) {
        this(manaAdjustment, false);
    }

    /**
     * Allows to cast the target card immediately, either for its cost or with a modifier (like for free, or mana as any type).
     */
    public MayCastTargetCardEffect(CastManaAdjustment manaAdjustment, boolean thenExile) {
        this(Duration.OneUse, manaAdjustment, TargetController.YOU, thenExile);
    }

    /**
     * Makes the target card playable for the specified duration as long as it remains in that zone.
     */
    public MayCastTargetCardEffect(Duration duration, boolean thenExile) {
        this(duration, CastManaAdjustment.NONE, TargetController.YOU, thenExile);
    }

    public MayCastTargetCardEffect(Duration duration, CastManaAdjustment manaAdjustment, TargetController targetController, boolean thenExile) {
        super(Outcome.Benefit);
        this.duration = duration;
        this.manaAdjustment = manaAdjustment;
        this.targetController = targetController;
        this.thenExile = thenExile;

        // TODO: support the non-yet-supported combinations.
        //       for now the constructor chains won't allow those.
        if (duration != Duration.OneUse) {
            switch (manaAdjustment) {
                case NONE:
                case WITHOUT_PAYING_MANA_COST:
                    break;
                default:
                    throw new IllegalStateException(
                            "Wrong code usage, not yet supported "
                                    + "duration={" + duration.name() + "}, "
                                    + "manaAdjustment={" + manaAdjustment.name() + "}"
                    );
            }
        }

        switch (targetController) {
            case OWNER: // card's owner.
                if (duration == duration.OneUse) {
                    // TODO: extend and test.
                    throw new IllegalStateException(
                            "Wrong code usage, not supported combination "
                                    + "targetController={" + targetController + "}"
                                    + "duration={" + duration + "}"
                    );
                }
                break;
            case YOU: // source controller.
                break;
            default:
                throw new IllegalStateException(
                        "Wrong code usage, not supported "
                                + "targetController={" + targetController + "}"
                );
        }
    }

    protected MayCastTargetCardEffect(final MayCastTargetCardEffect effect) {
        super(effect);
        this.duration = effect.duration;
        this.manaAdjustment = effect.manaAdjustment;
        this.targetController = effect.targetController;
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
        FixedTarget fixedTarget = new FixedTarget(card, game);
        Player caster = null;
        switch (targetController) {
            case YOU:
                caster = game.getPlayer(source.getControllerId());
                break;
            case OWNER:
                caster = game.getPlayer(card.getOwnerId());
                break;
            default:
                throw new IllegalStateException(
                        "Wrong code usage, not supported "
                                + "targetController={" + targetController + "}"
                );
        }
        if (caster == null) {
            return false;
        }

        if (duration == Duration.OneUse) {
            if (!caster.chooseUse(outcome, "Cast " + card.getLogName() + '?', source, game)) {
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
                            new YouMaySpendManaAsAnyColorToCastTargetEffect(Duration.Custom, caster.getId(), null);
                    effect.setTargetPointer(fixedTarget.copy());
                    game.addEffect(effect, source);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong code usage, manaAdjustment is not yet supported: " + manaAdjustment);
            }
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            boolean noMana = manaAdjustment == CastManaAdjustment.WITHOUT_PAYING_MANA_COST;
            CardUtil.castSingle(caster, source, game, card, noMana, null);
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        } else {
            switch (manaAdjustment) {
                case NONE:
                    CardUtil.makeCardPlayable(game, source, card, true, duration, false);
                    break;
                case WITHOUT_PAYING_MANA_COST:
                    // Alters only this way to cast the card. (e.g. alternative cast from Misthollow Griffin should not be affected.)
                    Ability sourceWithIdentifier = source.copy().setIdentifier(MageIdentifier.WithoutPayingManaCostAlternateCast);
                    ContinuousEffect effect = new PlayerCanPlayWithoutPayingManaEffect(duration, new MageObjectReference(card, game), caster.getId());
                    effect.setTargetPointer(fixedTarget.copy());
                    game.addEffect(effect, sourceWithIdentifier);
                    break;
                default:
                    // TODO: support (and add tests!) for the other mana adjustment
                    throw new IllegalArgumentException("Wrong code usage, manaAdjustment is not yet supported: " + manaAdjustment);
            }
        }
        if (thenExile) {
            ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(true);
            effect.setTargetPointer(fixedTarget.copy());
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String text = "";
        switch (targetController) {
            case YOU:
                text += "you";
                break;
            case OWNER:
                text += "its owner";
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage, targetController is not yet supported: " + targetController);
        }

        text += " may cast " + getTargetPointer().describeTargets(mode.getTargets(), "it");
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

// Note from Susucr:
// May be worth be a public effect? Not sure yet at this point of the rework of play/cast effects.
// Ideally as long-term goal MayCastTargetCardEffect should centralize all the different ways
// (potentially allowing to be extended for some of the non-standard scenarios.)
class PlayerCanPlayWithoutPayingManaEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;
    private final UUID playerId;

    public PlayerCanPlayWithoutPayingManaEffect(Duration duration, MageObjectReference mor, UUID playerId) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.mor = mor;
        this.playerId = playerId;
    }

    private PlayerCanPlayWithoutPayingManaEffect(final PlayerCanPlayWithoutPayingManaEffect effect) {
        super(effect);
        this.mor = effect.mor;
        this.playerId = effect.playerId;
    }

    @Override
    public PlayerCanPlayWithoutPayingManaEffect copy() {
        return new PlayerCanPlayWithoutPayingManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (mor.getCard(game) == null) {
            discard();
            return false;
        }
        if (!affectedControllerId.equals(playerId)) {
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        UUID mainId = theCard.getMainCard().getId(); // for split cards/MDFC/Adventure cards
        if (!mor.refersTo(mainId, game)) {
            return false;
        }
        allowCardToPlayWithoutMana(mainId, source, affectedControllerId, MageIdentifier.WithoutPayingManaCostAlternateCast, game);
        return true;
    }
}