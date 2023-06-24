package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ValueHint;
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
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class MetalworkColossus extends CardImpl {

    private static final String message = "Total mana value of noncreature artifacts you control";
    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifacts");

    public MetalworkColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // This spell costs {X} less to cast, where X is the total mana value of noncreature artifacts you control.
        DynamicValue xValue = new totalNonCreatureArtifactManaValue();
        this.addAbility(new SimpleStaticAbility(
                        Zone.ALL,
                        new SpellCostReductionSourceEffect(xValue)
                ).addHint(new ValueHint(message, xValue))
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

class totalNonCreatureArtifactManaValue implements DynamicValue {

    private static final String message = "the total mana value of noncreature artifacts you control";
    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("noncreature artifacts you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    totalNonCreatureArtifactManaValue() {
        // Nothing to do.
    }

    private totalNonCreatureArtifactManaValue(final totalNonCreatureArtifactManaValue dynamicValue) {
        super();
    }

    @Override
    public DynamicValue copy() {
        return new totalNonCreatureArtifactManaValue(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int totalCMC = 0;
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                filter,
                sourceAbility.getControllerId(),
                sourceAbility,
                game);
        for (Permanent permanent : permanents) {
            totalCMC += permanent.getManaValue();
        }
        return totalCMC;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "X";
    }
}
