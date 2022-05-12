package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MetalworkColossus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifacts");

    public MetalworkColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Metalwork Colossus costs {X} less to cast, where X is the total converted mana cost of noncreature artifacts you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MetalworkColossusCostReductionEffect()));

        // Sacrifice two artifacts: Return Metalwork Colossus from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new SacrificeTargetCost(new TargetControlledPermanent(2, filter))));
    }

    private MetalworkColossus(final MetalworkColossus card) {
        super(card);
    }

    @Override
    public MetalworkColossus copy() {
        return new MetalworkColossus(this);
    }
}

class MetalworkColossusCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("noncreature artifacts you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    MetalworkColossusCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {X} less to cast, where X is the total mana value of noncreature artifacts you control";
    }

    MetalworkColossusCostReductionEffect(final MetalworkColossusCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int totalCMC = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            totalCMC += permanent.getManaValue();
        }
        CardUtil.reduceCost(abilityToModify, totalCMC);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility);
    }

    @Override
    public MetalworkColossusCostReductionEffect copy() {
        return new MetalworkColossusCostReductionEffect(this);
    }
}
