package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.CopyTokenFunction;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class EncoreAbility extends ActivatedAbilityImpl {

    private final String rule;

    public EncoreAbility(Cost cost) {
        super(Zone.GRAVEYARD, new EncoreEffect(), cost);
        this.addCost(new ExileSourceFromGraveCost());
        this.timing = TimingRule.SORCERY;
        this.rule = setRule(cost);
    }

    public EncoreAbility(final EncoreAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public EncoreAbility copy() {
        return new EncoreAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    private String setRule(Cost cost) {
        return "Encore " + cost.getText() + " <i>(" + cost.getText() + ", " +
                "Exile this card from your graveyard: For each opponent, " +
                "create a token copy that attacks that opponent this turn if able. " +
                "They gain haste. Sacrifice them at the beginning of the next end step. " +
                "Activate only as a sorcery)</i>";
    }
}

class EncoreEffect extends OneShotEffect {

    EncoreEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private EncoreEffect(final EncoreEffect effect) {
        super(effect);
    }

    @Override
    public EncoreEffect copy() {
        return new EncoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        Token token = CopyTokenFunction.createTokenCopy(card, game);
        Set<MageObjectReference> addedTokens = new HashSet<>();
        int opponentCount = OpponentsCount.instance.calculate(game, source, this);
        if (opponentCount < 1) {
            return false;
        }
        token.putOntoBattlefield(opponentCount, game, source, source.getControllerId());
        Iterator<UUID> it = token.getLastAddedTokenIds().iterator();
        while (it.hasNext()) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                if (game.getPlayer(playerId) == null) {
                    continue;
                }
                UUID tokenId = it.next();
                MageObjectReference mageObjectReference = new MageObjectReference(tokenId, game);
                game.addEffect(new EncoreRequirementEffect(
                        mageObjectReference, playerId
                ), source);
                game.addEffect(new GainAbilityTargetEffect(
                        HasteAbility.getInstance(), Duration.Custom
                ).setTargetPointer(new FixedTarget(mageObjectReference)), source);
                addedTokens.add(mageObjectReference);
            }
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new EncoreSacrificeEffect(addedTokens)
        ), source);
        return true;
    }
}

class EncoreRequirementEffect extends RequirementEffect {

    private final MageObjectReference token;
    private final UUID playerId;

    EncoreRequirementEffect(MageObjectReference token, UUID playerId) {
        super(Duration.EndOfTurn);
        this.token = token;
        this.playerId = playerId;
    }

    private EncoreRequirementEffect(final EncoreRequirementEffect effect) {
        super(effect);
        this.token = effect.token;
        this.playerId = effect.playerId;
    }

    @Override
    public EncoreRequirementEffect copy() {
        return new EncoreRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return token.refersTo(permanent, game);
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return playerId;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}

class EncoreSacrificeEffect extends OneShotEffect {

    private final Set<MageObjectReference> mageObjectReferenceSet = new HashSet<>();

    EncoreSacrificeEffect(Set<MageObjectReference> mageObjectReferenceSet) {
        super(Outcome.Benefit);
        this.mageObjectReferenceSet.addAll(mageObjectReferenceSet);
        staticText = "sacrifice those tokens";
    }

    private EncoreSacrificeEffect(final EncoreSacrificeEffect effect) {
        super(effect);
        this.mageObjectReferenceSet.addAll(effect.mageObjectReferenceSet);
    }

    @Override
    public EncoreSacrificeEffect copy() {
        return new EncoreSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mageObjectReference : mageObjectReferenceSet) {
            Permanent permanent = mageObjectReference.getPermanent(game);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
