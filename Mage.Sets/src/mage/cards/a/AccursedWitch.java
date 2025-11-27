package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.DoubleFacedCardHalf;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author halljared
 */
public final class AccursedWitch extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("spells");

    public AccursedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN}, "{3}{B}",
                "Infectious Curse",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA, SubType.CURSE}, "B");
        this.getLeftHalfCard().setPT(4, 2);

        // Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new SpellsCostModificationThatTargetSourceEffect(-1, filter, TargetController.OPPONENT))
        );

        // When Accursed Witch dies, return it to the battlefield transformed under your control attached to target opponent.
        Ability ability = new DiesSourceTriggeredAbility(new AccursedWitchReturnTransformedEffect());
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Infectious Curse
        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Spells you cast that target enchanted player cost {1} less to cast.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfectiousCurseCostReductionEffect()));

        // At the beginning of enchanted player's upkeep, that player loses 1 life and you gain 1 life.
        Ability upkeepAbility = new BeginningOfUpkeepTriggeredAbility(
                TargetController.ENCHANTED, new LoseLifeTargetEffect(1).setText("that player loses 1 life"),
                false
        );
        upkeepAbility.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getRightHalfCard().addAbility(upkeepAbility);
    }

    private AccursedWitch(final AccursedWitch card) {
        super(card);
    }

    @Override
    public AccursedWitch copy() {
        return new AccursedWitch(this);
    }
}

class AccursedWitchReturnTransformedEffect extends OneShotEffect {

    AccursedWitchReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control attached to target opponent";
    }

    private AccursedWitchReturnTransformedEffect(final AccursedWitchReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public AccursedWitchReturnTransformedEffect copy() {
        return new AccursedWitchReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attachTo = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || !(game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) || attachTo == null) {
            return false;
        }

        DoubleFacedCardHalf card = (DoubleFacedCardHalf) game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.getState().setValue("attachTo:" + card.getOtherSide().getId(), attachTo.getId());
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            attachTo.addAttachment(card.getOtherSide().getId(), source, game);
        }
        return true;
    }
}
class InfectiousCurseCostReductionEffect extends CostModificationEffectImpl {

    InfectiousCurseCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells you cast that target enchanted player cost {1} less to cast";
    }

    private InfectiousCurseCostReductionEffect(InfectiousCurseCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!source.isControlledBy(abilityToModify.getControllerId())) {
            return false;
        }

        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }

        Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
        Set<UUID> allTargets;
        if (spell != null) {
            // real cast
            allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        } else {
            // playable
            allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);
        }

        // try to reduce all the time (if it possible to target)
        return allTargets.stream().anyMatch(target -> Objects.equals(target, enchantment.getAttachedTo()));
    }

    @Override
    public InfectiousCurseCostReductionEffect copy() {
        return new InfectiousCurseCostReductionEffect(this);
    }
}
