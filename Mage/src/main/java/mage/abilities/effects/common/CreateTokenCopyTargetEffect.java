package mage.abilities.effects.common;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.functions.ApplyToPermanent;
import mage.util.functions.EmptyApplyToPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class CreateTokenCopyTargetEffect extends OneShotEffect {

    private final UUID playerId;
    private final CardType additionalCardType;
    private boolean hasHaste;
    private final int number;
    private List<Permanent> addedTokenPermanents;
    private SubType additionalSubType;
    private SubType onlySubType;
    private boolean tapped;
    private boolean attacking;
    private UUID attackedPlayer;
    private final int tokenPower;
    private final int tokenToughness;
    private boolean gainsFlying;
    private boolean becomesArtifact;
    private ObjectColor color;
    private boolean useLKI = false;
    private boolean isntLegendary = false;

    public CreateTokenCopyTargetEffect(boolean useLKI) {
        this();
        this.useLKI = useLKI;
    }

    public CreateTokenCopyTargetEffect() {
        this((UUID) null);
    }

    public CreateTokenCopyTargetEffect(UUID playerId) {
        this(playerId, null, false);
    }

    public CreateTokenCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean hasHaste) {
        this(playerId, additionalCardType, hasHaste, 1);
    }

    public CreateTokenCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean hasHaste, int number) {
        this(playerId, additionalCardType, hasHaste, number, false, false);
    }

    /**
     * @param playerId           null the token is controlled/owned by the controller of
     *                           the source ability
     * @param additionalCardType the token gains this card type in addition
     * @param hasHaste           the token gains haste
     * @param number             number of tokens to put into play
     * @param tapped
     * @param attacking
     */
    public CreateTokenCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean hasHaste, int number, boolean tapped, boolean attacking) {
        this(playerId, additionalCardType, hasHaste, number, tapped, attacking, null);
    }

    public CreateTokenCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean hasHaste, int number, boolean tapped, boolean attacking, UUID attackedPlayer) {
        this(playerId, additionalCardType, hasHaste, number, tapped, attacking, attackedPlayer, Integer.MIN_VALUE, Integer.MIN_VALUE, false);
    }

    public CreateTokenCopyTargetEffect(UUID playerId, CardType additionalCardType, boolean hasHaste, int number, boolean tapped, boolean attacking, UUID attackedPlayer, int power, int toughness, boolean gainsFlying) {
        super(Outcome.PutCreatureInPlay);
        this.playerId = playerId;
        this.additionalCardType = additionalCardType;
        this.hasHaste = hasHaste;
        this.addedTokenPermanents = new ArrayList<>();
        this.number = number;
        this.tapped = tapped;
        this.attacking = attacking;
        this.attackedPlayer = attackedPlayer;
        this.tokenPower = power;
        this.tokenToughness = toughness;
        this.gainsFlying = gainsFlying;
    }

    public CreateTokenCopyTargetEffect(final CreateTokenCopyTargetEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.additionalCardType = effect.additionalCardType;
        this.hasHaste = effect.hasHaste;
        this.addedTokenPermanents = new ArrayList<>(effect.addedTokenPermanents);
        this.number = effect.number;
        this.additionalSubType = effect.additionalSubType;
        this.onlySubType = effect.onlySubType;
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
        this.attackedPlayer = effect.attackedPlayer;
        this.tokenPower = effect.tokenPower;
        this.tokenToughness = effect.tokenToughness;
        this.gainsFlying = effect.gainsFlying;
        this.becomesArtifact = effect.becomesArtifact;
        this.color = effect.color;
        this.useLKI = effect.useLKI;
        this.isntLegendary = effect.isntLegendary;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId;
        if (getTargetPointer() instanceof FixedTarget) {
            targetId = ((FixedTarget) getTargetPointer()).getTarget();
        } else {
            targetId = getTargetPointer().getFirst(game, source);
        }
        Permanent permanent;
        if (useLKI) {
            permanent = ((FixedTarget) getTargetPointer()).getTargetedPermanentOrLKIBattlefield(game);
        } else {
            permanent = game.getPermanentOrLKIBattlefield(targetId);
        }
        Card copyFrom;
        ApplyToPermanent applier = new EmptyApplyToPermanent();
        if (permanent != null) {
            // handle copies of copies
            Permanent copyFromPermanent = permanent;
            for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof CopyEffect) {
                    CopyEffect copyEffect = (CopyEffect) effect;
                    // there is another copy effect that our targetPermanent copies stats from
                    if (copyEffect.getSourceId().equals(permanent.getId())) {
                        MageObject object = ((CopyEffect) effect).getTarget();
                        if (object instanceof Permanent) {
                            copyFromPermanent = (Permanent) object;
                            if (copyEffect.getApplier() != null) {
                                applier = copyEffect.getApplier();
                            }
                        }
                    }
                }
            }
            copyFrom = copyFromPermanent;
        } else {
            copyFrom = game.getCard(getTargetPointer().getFirst(game, source));
        }

        if (copyFrom == null) {
            return false;
        }

        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(copyFrom); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        applier.apply(game, token, source, targetId);
        if (becomesArtifact) {
            token.addCardType(CardType.ARTIFACT);
        }
        if (isntLegendary) {
            token.getSuperType().remove(SuperType.LEGENDARY);
        }
        if (additionalCardType != null && !token.getCardType().contains(additionalCardType)) {
            token.addCardType(additionalCardType);
        }
        if (hasHaste) {
            token.addAbility(HasteAbility.getInstance());
        }
        if (gainsFlying) {
            token.addAbility(FlyingAbility.getInstance());
        }
        if (tokenPower != Integer.MIN_VALUE) {
            token.removePTCDA();
            token.getPower().modifyBaseValue(tokenPower);
        }
        if (tokenToughness != Integer.MIN_VALUE) {
            token.removePTCDA();
            token.getToughness().modifyBaseValue(tokenToughness);
        }
        if (additionalSubType != null && !token.hasSubtype(additionalSubType, game)) {
            token.getSubtype(game).add(additionalSubType);
        }
        if (onlySubType != null && !token.hasSubtype(onlySubType, game)) {
            token.getSubtype(game).clear();
            token.getSubtype(game).add(onlySubType);
        }
        if (color != null) {
            token.getColor(game).setColor(color);
        }

        token.putOntoBattlefield(number, game, source.getSourceId(), playerId == null ? source.getControllerId() : playerId, tapped, attacking, attackedPlayer);
        for (UUID tokenId : token.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                addedTokenPermanents.add(tokenPermanent);
            }
        }
        return true;
    }

    @Override
    public CreateTokenCopyTargetEffect copy() {
        return new CreateTokenCopyTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("create ");
        if (number == 1) {
            sb.append("a ");
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append("token that's a copy of target ");
        } else {
            sb.append(number);
            sb.append(" ");
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append("tokens that are copies of target ");
        }
        if (!mode.getTargets().isEmpty()) {
            sb.append(mode.getTargets().get(0).getTargetName());
        } else {
            throw new UnsupportedOperationException("Using default rule generation of target effect without having a target object");
        }

        if (attacking) {
            sb.append(" that are");
            if (tapped) {
                sb.append(" tapped and");
            }
            sb.append(" attacking");
        }
        return sb.toString();
    }

    public List<Permanent> getAddedPermanent() {
        return addedTokenPermanents;
    }

    public void setAdditionalSubType(SubType additionalSubType) {
        this.additionalSubType = additionalSubType;
    }

    public void setOnlySubType(SubType onlySubType) {
        this.onlySubType = onlySubType;
    }

    public void setOnlyColor(ObjectColor color) {
        this.color = color;
    }

    public void setUseLKI(boolean useLKI) {
        this.useLKI = useLKI;
    }

    public void setBecomesArtifact(boolean becomesArtifact) {
        this.becomesArtifact = becomesArtifact;
    }

    public void setIsntLegendary(boolean isntLegendary) {
        this.isntLegendary = isntLegendary;
    }

    public void setHasHaste(boolean hasHaste) {
        this.hasHaste = hasHaste;
    }

    public void exileTokensCreatedAtNextEndStep(Game game, Ability source) {
        for (Permanent tokenPermanent : addedTokenPermanents) {
            ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
            exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
        }
    }

    public void exileTokensCreatedAtEndOfCombat(Game game, Ability source) {
        for (Permanent tokenPermanent : addedTokenPermanents) {

            ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
            exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
            game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
        }
    }
}
