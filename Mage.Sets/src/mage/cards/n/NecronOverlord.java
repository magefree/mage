package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.game.Game;

/**
 *
 * @author @stwalsh4118
 */
public final class NecronOverlord extends CardImpl {

    public NecronOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{B}");
        
        this.subtype.add(SubType.NECRON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Relentless Mind -- {X}, {T}, Tap X untapped artifacts you control: Target opponent loses X life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(GetXValue.instance), new ManaCostsImpl<>("{X}"));
        ability.addTarget(new TargetOpponent());
        ability.addCost(new TapSourceCost());
        ability.addCost(new NecronOverlordTapVariableArtifactCost().setText("Tap X untapped artifacts you control"));
        ability.withFlavorWord("Relentless Mind");
        this.addAbility(ability);

    }

    private NecronOverlord(final NecronOverlord card) {
        super(card);
    }

    @Override
    public NecronOverlord copy() {
        return new NecronOverlord(this);
    }
}

class NecronOverlordTapVariableArtifactCost extends VariableCostImpl {

    public NecronOverlordTapVariableArtifactCost() {
        this( 0);
    }

    public NecronOverlordTapVariableArtifactCost(String text) {
        this(0, text);
    }

    public NecronOverlordTapVariableArtifactCost(int minimalCountersToPay) {
        this( minimalCountersToPay, "");
    }

    public NecronOverlordTapVariableArtifactCost(int minimalCountersToPay, String text) {
        super(VariableCostType.NORMAL, "x");
    }

    public NecronOverlordTapVariableArtifactCost(final NecronOverlordTapVariableArtifactCost cost) {
        super(cost);
    }

    @Override
    public NecronOverlordTapVariableArtifactCost copy() {
        return new NecronOverlordTapVariableArtifactCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetControlledPermanent target = new TargetControlledPermanent(xValue, xValue, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, true);
        Cost targetCost = new TapTargetCost(target);
        return targetCost;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        return source.getManaCostsToPay().getX();
    }
}
