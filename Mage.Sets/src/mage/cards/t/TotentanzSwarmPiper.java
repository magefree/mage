package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.permanent.token.RatCantBlockToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TotentanzSwarmPiper extends CardImpl {

    private static final FilterControlledCreaturePermanent filterRat =
            new FilterControlledCreaturePermanent(SubType.RAT, "attacking Rat you control");

    static {
        filterRat.add(AttackingPredicate.instance);
    }

    public TotentanzSwarmPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Totentanz, Swarm Piper or another nontoken creature you control dies, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new RatCantBlockToken()), false,
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ));

        // {1}{B}: Target attacking Rat you control gains deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addTarget(new TargetControlledCreaturePermanent(filterRat));
        this.addAbility(ability);
    }

    private TotentanzSwarmPiper(final TotentanzSwarmPiper card) {
        super(card);
    }

    @Override
    public TotentanzSwarmPiper copy() {
        return new TotentanzSwarmPiper(this);
    }
}
