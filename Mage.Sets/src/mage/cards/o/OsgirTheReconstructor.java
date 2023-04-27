
package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author Arketec
 */
public final class OsgirTheReconstructor extends CardImpl {

    public OsgirTheReconstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, Sacrifice an artifact: Target creature you control gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN)));
        this.addAbility(ability);

        // {X},{T}, Exile an artifact with mana value X from your graveyard: Create two tokens that are copies of the exiled card. Activate only as
        Ability copyAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD,
                new OsgirTheReconstructorCreateArtifactTokensEffect(),
                new ManaCostsImpl<>("{X}"));
        copyAbility.addCost(new TapSourceCost());
        copyAbility.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(), "Exile an artifact card with mana value X from your graveyard"));

        copyAbility.setCostAdjuster(OsgirTheReconstructorCostAdjuster.instance);

        this.addAbility(copyAbility);
    }

    private OsgirTheReconstructor(final OsgirTheReconstructor card) {
        super(card);
    }

    @Override
    public OsgirTheReconstructor copy() {
        return new OsgirTheReconstructor(this);
    }
}

enum OsgirTheReconstructorCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        FilterCard filter = new FilterArtifactCard("an artifact card with mana value "+xValue+" from your graveyard");
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        for (Cost cost: ability.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                cost.getTargets().set(0, new TargetCardInYourGraveyard(filter));
            }
        }
    }
}

class OsgirTheReconstructorCreateArtifactTokensEffect extends OneShotEffect {

    public OsgirTheReconstructorCreateArtifactTokensEffect() {
        super(Outcome.Benefit);
        this.staticText = "Create two tokens that are copies of the exiled card.";
    }

    public OsgirTheReconstructorCreateArtifactTokensEffect(final OsgirTheReconstructorCreateArtifactTokensEffect effect)  {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = null;
        for (Cost cost : source.getCosts()) {
            if (!(cost instanceof ExileFromGraveCost)) {
                continue;
            }
            card = ((ExileFromGraveCost) cost).getExiledCards().get(0);
        }

        if (player == null || card == null) {
            return false;
        }

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(player.getId(), null, false, 2);
        effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
        effect.apply(game, source);

        return  true;
    }

    @Override
    public OsgirTheReconstructorCreateArtifactTokensEffect  copy() {
        return new OsgirTheReconstructorCreateArtifactTokensEffect(this);
    }
}
