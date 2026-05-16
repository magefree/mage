package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental33BlueRedToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author muz
 */
public final class FurygaleFlocking extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", xValue);

    public FurygaleFlocking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");

        // This spell costs {1} less to cast for each instant and sorcery card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).setRuleAtTheTop(true).addHint(hint));

        // For each opponent, create two 3/3 blue and red Elemental creature tokens with flying that attack that opponent this turn if able. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new FurygaleFlockingEffect());
    }

    private FurygaleFlocking(final FurygaleFlocking card) {
        super(card);
    }

    @Override
    public FurygaleFlocking copy() {
        return new FurygaleFlocking(this);
    }
}

class FurygaleFlockingEffect extends OneShotEffect {

    FurygaleFlockingEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "for each opponent, create two 3/3 blue and red Elemental creature tokens with flying "
                + "that attack that opponent this turn if able. They gain haste until end of turn";
    }

    private FurygaleFlockingEffect(final FurygaleFlockingEffect effect) {
        super(effect);
    }

    @Override
    public FurygaleFlockingEffect copy() {
        return new FurygaleFlockingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new Elemental33BlueRedToken();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(2, game, source, source.getControllerId());
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                        .setTargetPointer(new FixedTarget(tokenId, game)), source);
                game.addEffect(new FurygaleFlockingAttackEffect(opponentId)
                        .setTargetPointer(new FixedTarget(tokenId, game)), source);
            }
        }
        return true;
    }
}

class FurygaleFlockingAttackEffect extends RequirementEffect {

    private final UUID opponentId;

    FurygaleFlockingAttackEffect(UUID opponentId) {
        super(Duration.EndOfTurn);
        this.opponentId = opponentId;
    }

    private FurygaleFlockingAttackEffect(final FurygaleFlockingAttackEffect effect) {
        super(effect);
        this.opponentId = effect.opponentId;
    }

    @Override
    public FurygaleFlockingAttackEffect copy() {
        return new FurygaleFlockingAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean mustAttack(Game game) {
        Player opponent = game.getPlayer(opponentId);
        return opponent != null && opponent.isInGame();
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        Player opponent = game.getPlayer(opponentId);
        return (opponent != null && opponent.isInGame()) ? opponentId : null;
    }
}
