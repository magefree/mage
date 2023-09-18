package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyrantGuard extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public TyrantGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{2}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Shieldwall -- Sacrifice Tyrant Guard: Creatures you control with counters on them gain hexproof and indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityAllEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, filter,
                "creatures you control with counters on them gain hexproof"
        ), new SacrificeSourceCost());
        ability.addEffect(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                filter, "and indestructible until end of turn"
        ));
        this.addAbility(ability.withFlavorWord("Shieldwall"));
    }

    private TyrantGuard(final TyrantGuard card) {
        super(card);
    }

    @Override
    public TyrantGuard copy() {
        return new TyrantGuard(this);
    }
}
