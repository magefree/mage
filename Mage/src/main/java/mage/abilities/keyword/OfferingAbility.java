package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.HybridManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.GameLog;

import java.util.Objects;
import java.util.UUID;

/**
 * 702.46. Offering # 702.46a Offering is a static ability of a card that
 * functions in any zone from which the card can be cast. "[Subtype] offering"
 * means "You may cast this card any time you could cast an instant by
 * sacrificing a [subtype] permanent. If you do, the total cost to cast this
 * card is reduced by the sacrificed permanent's mana cost." #
 * <p>
 * 702.46b The permanent is sacrificed at the same time the spell is announced
 * (see rule 601.2a). The total cost of the spell is reduced by the sacrificed
 * permanent's mana cost (see rule 601.2e). #
 * <p>
 * 702.46c Generic mana in the sacrificed permanent's mana cost reduces generic
 * mana in the total cost to cast the card with offering. Colored mana in the
 * sacrificed permanent's mana cost reduces mana of the same color in the total
 * cost to cast the card with offering. Colored mana in the sacrificed
 * permanent's mana cost that doesn't match colored mana in the colored mana
 * cost of the card with offering, or is in excess of the card's colored mana
 * cost, reduces that much generic mana in the total cost. #
 *
 * @author LevelX2
 */
public class OfferingAbility extends StaticAbility implements AlternateManaPaymentAbility {

    private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    /**
     * @param subtype name of the subtype that can be offered
     */
    public OfferingAbility(SubType subtype) {
        super(Zone.ALL, null);
        filter.add(subtype.getPredicate());
        filter.setMessage(subtype.getDescription());
        this.addEffect(new OfferingAsThoughEffect());
    }

    public OfferingAbility(OfferingAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public OfferingAbility copy() {
        return new OfferingAbility(this);
    }

    public FilterControlledCreaturePermanent getFilter() {
        return filter;
    }

    @Override
    public String getRule(boolean all) {
        String subtype = filter.getMessage();
        return subtype + " offering <i>(You may cast this card any time you could cast an instant by sacrificing a " + subtype + " and paying the difference in mana costs between this and the sacrificed " + subtype + ". Mana cost includes color.)</i>";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.NORMAL;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        // No special Action
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions additionalManaOptionsForThisAbility = new ManaOptions();

        // Creatures from the offerd type
        game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(Card::getSpellAbility)
                .filter(Objects::nonNull)
                .forEach(spellAbility -> {
                            ManaOptions manaOptionsForThisPermanent = new ManaOptions();
                            for (ManaCost manaCost : spellAbility.getManaCosts()) {
                                if (manaCost instanceof HybridManaCost) {
                                    ManaOptions manaOptionsForHybrid = new ManaOptions();
                                    manaOptionsForHybrid.addAll(manaCost.getManaOptions());
                                    manaOptionsForThisPermanent.addMana(manaOptionsForHybrid);
                                } else {
                                    manaOptionsForThisPermanent.addMana(manaCost.getMana());
                                }
                            }

                            additionalManaOptionsForThisAbility.addAll(manaOptionsForThisPermanent);
                        }
                );

        additionalManaOptionsForThisAbility.removeFullyIncludedVariations();
        return additionalManaOptionsForThisAbility;
    }
}

class OfferingAsThoughEffect extends AsThoughEffectImpl {

    public OfferingAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfGame, Outcome.Benefit);
    }

    public OfferingAsThoughEffect(final OfferingAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OfferingAsThoughEffect copy() {
        return new OfferingAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }

    @Override
    public boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(sourceId);
            if (card == null || !card.isOwnedBy(source.getControllerId())) {
                return false;
            }
            // because can activate is always called twice, result from first call will be used
            Object object = game.getState().getValue("offering_" + card.getId());
            if (object != null && object.equals(true)) {
                Object alreadyConfirmed = game.getState().getValue("offering_ok_" + card.getId());
                game.getState().setValue("offering_" + card.getId(), null);
                game.getState().setValue("offering_ok_" + card.getId(), null);
                return alreadyConfirmed != null;
            } else {
                // first call -> remove previous Ids
                game.getState().setValue("offering_Id_" + card.getId(), null);
            }

            if (game.getBattlefield().count(((OfferingAbility) source).getFilter(), source.getControllerId(), source, game) > 0) {

                if (game.inCheckPlayableState()) {
                    return true;
                }
                FilterControlledCreaturePermanent filter = ((OfferingAbility) source).getFilter();
                Card spellToCast = game.getCard(source.getSourceId());
                if (spellToCast == null) {
                    return false;
                }
                Player player = game.getPlayer(source.getControllerId());
                if (player != null
                        && player.chooseUse(Outcome.Benefit, "Offer a " + filter.getMessage() + " to cast " + spellToCast.getName() + '?', source, game)) {
                    Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
                    player.chooseTarget(Outcome.Sacrifice, target, source, game);
                    if (!target.isChosen()) {
                        return false;
                    }
                    game.getState().setValue("offering_" + card.getId(), true);
                    Permanent offer = game.getPermanent(target.getFirstTarget());
                    if (offer != null) {
                        UUID activationId = UUID.randomUUID();
                        OfferingCostReductionEffect effect = new OfferingCostReductionEffect(activationId);
                        effect.setTargetPointer(new FixedTarget(offer, game));
                        game.addEffect(effect, source);
                        game.getState().setValue("offering_ok_" + card.getId(), true);
                        game.getState().setValue("offering_Id_" + card.getId(), activationId);
                        game.informPlayers(player.getLogName() + " announces to offer "
                                + offer.getLogName() + " to cast "
                                + GameLog.getColoredObjectName(spellToCast));// No id name to prevent to offer hand card knowledge after cancel casting
                        return true;
                    }
                } else {
                    game.getState().setValue("offering_" + card.getId(), true);
                }
            }
        }
        return false;
    }
}

class OfferingCostReductionEffect extends CostModificationEffectImpl {

    private final UUID activationId;

    OfferingCostReductionEffect(UUID activationId) {
        super(Duration.OneUse, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.activationId = activationId;
        staticText = "mana costs reduction from offering";
    }

    OfferingCostReductionEffect(OfferingCostReductionEffect effect) {
        super(effect);
        this.activationId = effect.activationId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Permanent toOffer = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (toOffer != null) {
            toOffer.sacrifice(source, game);
            CardUtil.reduceCost((SpellAbility) abilityToModify, toOffer.getSpellAbility().getManaCosts());
        }
        game.getState().setValue("offering_" + source.getSourceId(), null);
        game.getState().setValue("offering_ok_" + source.getSourceId(), null);
        this.discard();
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (game.inCheckPlayableState()) { // Cost modifaction does not work correctly for checking available spells
            return false;
        }
        if (abilityToModify.getSourceId().equals(source.getSourceId())
                && abilityToModify instanceof SpellAbility) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                Object object = game.getState().getValue("offering_Id_" + card.getId());
                if (object != null && object.equals(this.activationId) && getTargetPointer().getFirst(game, source) != null) {
                    return true;
                }
            }
            // to target to offer, no correct activation ID this effect is no longer valid
            this.discard();
        }
        return false;
    }

    @Override
    public OfferingCostReductionEffect copy() {
        return new OfferingCostReductionEffect(this);
    }
}
