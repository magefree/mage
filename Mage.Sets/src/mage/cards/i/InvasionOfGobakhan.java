package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfGobakhan extends TransformingDoubleFacedCard {

    public InvasionOfGobakhan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{1}{W}",
                "Lightshield Array",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "W"
        );

        // Invasion of Gobakhan
        this.getLeftHalfCard().setStartingDefense(3);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Gobakhan enters the battlefield, look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InvasionOfGobakhanEffect());
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Lightshield Array
        // At the beginning of your end step, put a +1/+1 counter on each creature that attacked this turn.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new LightshieldArrayEffect()));

        // Sacrifice Lightshield Array: Creatures you control gain hexproof and indestructible until end of turn.
        Ability rightAbility = new SimpleActivatedAbility(
                new GainAbilityControlledEffect(
                        HexproofAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("creatures you control gain hexproof"), new SacrificeSourceCost()
        );
        rightAbility.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and indestructible until end of turn"));
        this.getRightHalfCard().addAbility(rightAbility);
    }

    private InvasionOfGobakhan(final InvasionOfGobakhan card) {
        super(card);
    }

    @Override
    public InvasionOfGobakhan copy() {
        return new InvasionOfGobakhan(this);
    }
}

class InvasionOfGobakhanEffect extends OneShotEffect {

    InvasionOfGobakhanEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand. You may exile a nonland card from it. " +
                "For as long as that card remains exiled, its owner may play it. " +
                "A spell cast this way costs {2} more to cast";
    }

    private InvasionOfGobakhanEffect(final InvasionOfGobakhanEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfGobakhanEffect copy() {
        return new InvasionOfGobakhanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
        controller.choose(outcome, opponent.getHand(), target, source, game);
        Card card = opponent.getHand().get(target.getFirstTarget(), game);
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        game.addEffect(new InvasionOfGobakhanCastEffect(card, game), source);
        game.addEffect(new InvasionOfGobakhanCostEffect(card, game), source);
        return true;
    }
}

class InvasionOfGobakhanCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    InvasionOfGobakhanCastEffect(Card card, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
    }

    private InvasionOfGobakhanCastEffect(final InvasionOfGobakhanCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public InvasionOfGobakhanCastEffect copy() {
        return new InvasionOfGobakhanCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = mor.getCard(game);
        if (card == null) {
            discard();
            return false;
        }
        return mor.refersTo(CardUtil.getMainCardId(game, sourceId), game) && card.isOwnedBy(affectedControllerId);
    }
}

class InvasionOfGobakhanCostEffect extends CostModificationEffectImpl {

    private final MageObjectReference mor;

    InvasionOfGobakhanCostEffect(Card card, Game game) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.INCREASE_COST);
        mor = new MageObjectReference(card, game, 1);
    }

    private InvasionOfGobakhanCostEffect(InvasionOfGobakhanCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        if (game.inCheckPlayableState()) {
            UUID cardToCheckId = CardUtil.getMainCardId(game, abilityToModify.getSourceId());
            return mor.getSourceId().equals(cardToCheckId)
                    && mor.getZoneChangeCounter() == game.getState().getZoneChangeCounter(cardToCheckId) + 1;
        } else {
            return mor.refersTo(CardUtil.getMainCardId(game, abilityToModify.getSourceId()), game);
        }
    }

    @Override
    public InvasionOfGobakhanCostEffect copy() {
        return new InvasionOfGobakhanCostEffect(this);
    }
}

class LightshieldArrayEffect extends OneShotEffect {

    LightshieldArrayEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each creature that attacked this turn";
    }

    private LightshieldArrayEffect(final LightshieldArrayEffect effect) {
        super(effect);
    }

    @Override
    public LightshieldArrayEffect copy() {
        return new LightshieldArrayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mor : game
                .getState()
                .getWatcher(AttackedThisTurnWatcher.class)
                .getAttackedThisTurnCreatures()) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
