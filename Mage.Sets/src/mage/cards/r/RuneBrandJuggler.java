package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.SuspectedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuneBrandJuggler extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a suspected creature");

    static {
        filter.add(SuspectedPredicate.instance);
    }

    public RuneBrandJuggler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rune-Brand Juggler enters the battlefield, suspect up to one target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SuspectTargetEffect());
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // {3}{B}{R}, Sacrifice a suspected creature: Target creature gets -5/-5 until end of turn.
        ability = new SimpleActivatedAbility(
                new BoostTargetEffect(-5, -5), new ManaCostsImpl<>("{3}{B}{R}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RuneBrandJuggler(final RuneBrandJuggler card) {
        super(card);
    }

    @Override
    public RuneBrandJuggler copy() {
        return new RuneBrandJuggler(this);
    }
}
