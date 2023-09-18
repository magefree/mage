package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.BloodAvatarToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExtusOriqOverlord extends ModalDoubleFacedCard {

    private static final FilterCard filter
            = new FilterCreatureCard("nonlegendary creature card from your graveyard");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public ExtusOriqOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "{1}{W}{B}{B}",
                "Awaken the Blood Avatar",
                new SuperType[]{}, new CardType[]{CardType.SORCERY}, new SubType[]{}, "{6}{B}{R}"
        );

        // 1.
        // Extus, Oriq Overlord
        // Legendary Creature - Human Warlock
        this.getLeftHalfCard().setPT(2, 4);

        // Double strike
        this.getLeftHalfCard().addAbility(DoubleStrikeAbility.getInstance());

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, return target nonlegendary creature card from your graveyard to your hand.
        Ability ability = new MagecraftAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Awaken the Blood Avatar
        // Sorcery
        // As an additional cost to cast this spell, you may sacrifice any number of creatures. This spell costs {2} less to cast for each creature sacrificed this way.
        Cost cost = new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true);
        cost.setText("As an additional cost to cast this spell, you may sacrifice any number of creatures. " +
                "This spell costs {2} less to cast for each creature sacrificed this way");
        this.getRightHalfCard().getSpellAbility().addCost(cost);
        ability = new SimpleStaticAbility(Zone.ALL, new AwakenTheBloodAvatarCostReductionEffect());
        ability.setRuleVisible(false);
        this.getRightHalfCard().addAbility(ability);

        // Each opponent sacrifices a creature. Create a 3/6 black and red Avatar creature token with haste and "Whenever this creature attacks, it deals 3 damage to each opponent."
        this.getRightHalfCard().getSpellAbility().addEffect(new SacrificeOpponentsEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ));
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new BloodAvatarToken()));
    }

    private ExtusOriqOverlord(final ExtusOriqOverlord card) {
        super(card);
    }

    @Override
    public ExtusOriqOverlord copy() {
        return new ExtusOriqOverlord(this);
    }
}

class AwakenTheBloodAvatarCostReductionEffect extends CostModificationEffectImpl {

    AwakenTheBloodAvatarCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private AwakenTheBloodAvatarCostReductionEffect(final AwakenTheBloodAvatarCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        for (Cost cost : spellAbility.getCosts()) {
            if (!(cost instanceof SacrificeXTargetCost)) {
                continue;
            }
            if (game.inCheckPlayableState()) {
                // allows to cast in getPlayable
                int reduction = ((SacrificeXTargetCost) cost).getMaxValue(spellAbility, game);
                CardUtil.adjustCost(spellAbility, reduction * 2);
            } else {
                // real cast
                int reduction = ((SacrificeXTargetCost) cost).getAmount();
                CardUtil.adjustCost(spellAbility, reduction * 2);
            }

            break;
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public AwakenTheBloodAvatarCostReductionEffect copy() {
        return new AwakenTheBloodAvatarCostReductionEffect(this);
    }
}
