package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class CauterySliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "All Slivers");
    private static final FilterPermanentOrPlayer filter2
            = new FilterPermanentOrPlayer("player, planeswalker, or Sliver creature");

    static {
        filter2.getPermanentFilter().add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        SubType.SLIVER.getPredicate()
                )
        ));
    }

    public CauterySliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{1}, Sacrifice this permanent: This permanent deals 1 damage to any target."
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1, "this permanent"), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield, filter, "All Slivers have \"{1}, " +
                "Sacrifice this permanent: This permanent deals 1 damage to any target.\""
        )));

        // All Slivers have "{1}, Sacrifice this permanent: Prevent the next 1 damage that would be dealt to target player, planeswalker, or Sliver creature this turn."
        ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanentOrPlayer(filter2));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield, filter, "All Slivers have " +
                "\"{1}, Sacrifice this permanent: Prevent the next 1 damage " +
                "that would be dealt to target player, planeswalker, or Sliver creature this turn.\""
        )));
    }

    private CauterySliver(final CauterySliver card) {
        super(card);
    }

    @Override
    public CauterySliver copy() {
        return new CauterySliver(this);
    }
}
