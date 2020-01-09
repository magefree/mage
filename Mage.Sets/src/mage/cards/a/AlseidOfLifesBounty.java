package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlseidOfLifesBounty extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("creature or enchantment you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public AlseidOfLifesBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {1}, Sacrifice Alseid of Life's Bounty: Target creature or enchantment you control gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AlseidOfLifesBounty(final AlseidOfLifesBounty card) {
        super(card);
    }

    @Override
    public AlseidOfLifesBounty copy() {
        return new AlseidOfLifesBounty(this);
    }
}
