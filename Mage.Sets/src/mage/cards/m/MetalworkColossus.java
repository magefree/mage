package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.TotalPermanentsManaValue;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author emerald000, Susucr
 */
public final class MetalworkColossus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifacts");

    private static final FilterPermanent filterCostReduction = new FilterControlledArtifactPermanent("noncreature artifacts you control");

    static {
        filterCostReduction.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    private static final TotalPermanentsManaValue xValue = new TotalPermanentsManaValue(filterCostReduction);

    public MetalworkColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // This spell costs {X} less to cast, where X is the total mana value of noncreature artifacts you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(xValue)
            ).addHint(xValue.getHint()).setRuleAtTheTop(true)
        );

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