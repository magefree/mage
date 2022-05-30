package mage.abilities.effects.common;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
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
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;
import mage.util.functions.EmptyCopyApplier;

import java.util.*;

/**
 * @author LevelX2
 */
public class CreateTokenCopyTargetEffect extends OneShotEffect {

    private final Set<Class<? extends Ability>> abilityClazzesToRemove;
    private final List<Permanent> addedTokenPermanents;
    private final List<Ability> additionalAbilities;
    private final CardType additionalCardType;
    private SubType additionalSubType;
    private final UUID attackedPlayer;
    private final boolean attacking;
    private boolean becomesArtifact;
    private ObjectColor color;
    private CounterType counter;
    private final boolean gainsFlying;
    private boolean hasHaste;
    private boolean isntLegendary = false;
    private int number;
    private int numberOfCounters;
    private SubType onlySubType;
    private final UUID playerId;
    private final boolean tapped;
    private Permanent savedPermanent = null;
    private int startingLoyalty = -1;
    private final int tokenPower;
    private final int tokenToughness;
    private boolean useLKI = false;


    public CreateTokenCopyTargetEffect(boolean useLKI) {
        this();
        this.useLKI = useLKI;
    }

    public CreateTokenCopyTargetEffect() {
        this((UUID) null);
    }

    public CreateTokenCopyTargetEffect(CounterType counter, int numberOfCounters) {
        this((UUID) null);
        this.counter = counter;
        this.numberOfCounters = numberOfCounters;
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

        this.abilityClazzesToRemove = new HashSet<>();
        this.additionalAbilities = new ArrayList<>();
    }

    public CreateTokenCopyTargetEffect(final CreateTokenCopyTargetEffect effect) {
        super(effect);

        this.abilityClazzesToRemove = new HashSet<>(effect.abilityClazzesToRemove);
        this.addedTokenPermanents = new ArrayList<>(effect.addedTokenPermanents);
        this.additionalAbilities = new ArrayList<>(effect.additionalAbilities);
        this.additionalCardType = effect.additionalCardType;
        this.additionalSubType = effect.additionalSubType;
        this.attackedPlayer = effect.attackedPlayer;
        this.attacking = effect.attacking;
        this.becomesArtifact = effect.becomesArtifact;
        this.color = effect.color;
        this.counter = effect.counter;
        this.gainsFlying = effect.gainsFlying;
        this.hasHaste = effect.hasHaste;
        this.isntLegendary = effect.isntLegendary;
        this.number = effect.number;
        this.numberOfCounters = effect.numberOfCounters;
        this.onlySubType = effect.onlySubType;
        this.playerId = effect.playerId;
        this.savedPermanent = effect.savedPermanent;
        this.startingLoyalty = effect.startingLoyalty;
        this.tapped = effect.tapped;
        this.tokenPower = effect.tokenPower;
        this.tokenToughness = effect.tokenToughness;
        this.useLKI = effect.useLKI;
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
        if (savedPermanent != null) {
            permanent = savedPermanent;
        } else if (useLKI) {
            permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        } else {
            permanent = game.getPermanentOrLKIBattlefield(targetId);
        }

        // can target card or permanent
        Card copyFrom;
        CopyApplier applier = new EmptyCopyApplier();
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

        // create token and modify all attributes permanently (without game usage)
        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(copyFrom, game); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        applier.apply(game, token, source, targetId);
        if (becomesArtifact) {
            token.addCardType(CardType.ARTIFACT);
        }
        if (isntLegendary) {
            token.getSuperType().remove(SuperType.LEGENDARY);
        }

        if (startingLoyalty != -1) {
            token.setStartingLoyalty(startingLoyalty);
        }
        if (additionalCardType != null) {
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
        if (onlySubType != null) {
            token.removeAllCreatureTypes();
            token.addSubType(onlySubType);
        }
        if (additionalSubType != null) {
            token.addSubType(additionalSubType);
        }
        if (color != null) {
            token.getColor().setColor(color);
        }
        additionalAbilities.stream().forEach(token::addAbility);

        if (!this.abilityClazzesToRemove.isEmpty()) {
            List<Ability> abilitiesToRemoveTmp = new ArrayList<>();

            // Find the ones to remove
            for (Ability ability : token.getAbilities()) {
                if (this.abilityClazzesToRemove.contains(ability.getClass())) {
                    abilitiesToRemoveTmp.add(ability);
                }
            }

            // Remove them
            for (Ability ability : abilitiesToRemoveTmp) {
                // Remove subabilities
                token.removeAbilities(ability.getSubAbilities());
                // Remove the ability
                token.removeAbility(ability);
            }
        }

        token.putOntoBattlefield(number, game, source, playerId == null ? source.getControllerId() : playerId, tapped, attacking, attackedPlayer);
        for (UUID tokenId : token.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                addedTokenPermanents.add(tokenPermanent);
                // add counters if necessary ie Ochre Jelly
                if (counter != null
                        && numberOfCounters > 0) {
                    tokenPermanent.addCounters(counter.createInstance(numberOfCounters), source.getControllerId(), source, game);
                }
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
            sb.append("token that's a copy of ");
        } else {
            sb.append(number);
            sb.append(" ");
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append("tokens that are copies of ");
        }
        if (mode.getTargets().isEmpty()) {
            throw new UnsupportedOperationException("Using default rule generation of target effect without having a target object");
        }
        String targetName = mode.getTargets().get(0).getTargetName();
        if (!targetName.startsWith("another target")) {
            sb.append("target ");
        }
        sb.append(targetName);

        if (attacking) {
            sb.append(" that are");
            if (tapped) {
                sb.append(" tapped and");
            }
            sb.append(" attacking");
        }
        return sb.toString();
    }

    public List<Permanent> getAddedPermanents() {
        return addedTokenPermanents;
    }

    public CreateTokenCopyTargetEffect setAdditionalSubType(SubType additionalSubType) {
        this.additionalSubType = additionalSubType;
        return this;
    }

    public CreateTokenCopyTargetEffect setOnlySubType(SubType onlySubType) {
        this.onlySubType = onlySubType;
        return this;
    }

    public CreateTokenCopyTargetEffect setOnlyColor(ObjectColor color) {
        this.color = color;
        return this;
    }

    public CreateTokenCopyTargetEffect setUseLKI(boolean useLKI) {
        this.useLKI = useLKI;
        return this;
    }

    public CreateTokenCopyTargetEffect setBecomesArtifact(boolean becomesArtifact) {
        this.becomesArtifact = becomesArtifact;
        return this;
    }

    public CreateTokenCopyTargetEffect setIsntLegendary(boolean isntLegendary) {
        this.isntLegendary = isntLegendary;
        return this;
    }

    public CreateTokenCopyTargetEffect setHasHaste(boolean hasHaste) {
        this.hasHaste = hasHaste;
        return this;
    }

    public CreateTokenCopyTargetEffect setStartingLoyalty(int startingLoyalty) {
        this.startingLoyalty = startingLoyalty;
        return this;
    }

    public CreateTokenCopyTargetEffect setNumber(int number) {
        this.number = number;
        return this;
    }

    public CreateTokenCopyTargetEffect addAbilityClassesToRemoveFromTokens(Class<? extends Ability> clazz) {
        this.abilityClazzesToRemove.add(clazz);return this;
    }

    public CreateTokenCopyTargetEffect addAdditionalAbilities(Ability... abilities) {
        this.additionalAbilities.addAll(Arrays.asList(abilities));
        return this;
    }


    public CreateTokenCopyTargetEffect setSavedPermanent(Permanent savedPermanent) {
        this.savedPermanent = savedPermanent;
        return this;
    }

    public void sacrificeTokensCreatedAtNextEndStep(Game game, Ability source) {
        this.removeTokensCreatedAtEndOf(game, source, PhaseStep.END_TURN, false);
    }

    public void exileTokensCreatedAtNextEndStep(Game game, Ability source) {
        this.removeTokensCreatedAtEndOf(game, source, PhaseStep.END_TURN, true);
    }

    public void sacrificeTokensCreatedAtEndOfCombat(Game game, Ability source) {
        this.removeTokensCreatedAtEndOf(game, source, PhaseStep.END_COMBAT, false);
    }

    public void exileTokensCreatedAtEndOfCombat(Game game, Ability source) {
        this.removeTokensCreatedAtEndOf(game, source, PhaseStep.END_COMBAT, true);
    }

    private void removeTokensCreatedAtEndOf(Game game, Ability source, PhaseStep phaseStepToExileCards, boolean exile) {
        Effect effect;
        if (exile) {
            effect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD).setText("exile the token copies");
        } else {
            effect = new SacrificeTargetEffect("sacrifice the token copies", source.getControllerId());
        }
        effect.setTargetPointer(new FixedTargets(addedTokenPermanents, game));

        DelayedTriggeredAbility exileAbility;

        switch (phaseStepToExileCards) {
            case END_TURN:
                exileAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                break;
            case END_COMBAT:
                exileAbility = new AtTheEndOfCombatDelayedTriggeredAbility(effect);
                break;
            default:
                return;
        }

        game.addDelayedTriggeredAbility(exileAbility, source);
    }
}
