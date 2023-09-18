package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsAdjuster;

/**
 *
 * @author TheElk801
 */
public final class RunedArch extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures with power 2 or less");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public RunedArch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Runed Arch enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {X}, {tap}, Sacrifice Runed Arch: X target creatures with power 2 or less are unblockable this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfGame)
                        .setText("X target creatures with power 2 or less can't be blocked this turn."),
                new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(XTargetsAdjuster.instance);
        this.addAbility(ability);
    }

    private RunedArch(final RunedArch card) {
        super(card);
    }

    @Override
    public RunedArch copy() {
        return new RunedArch(this);
    }
}
