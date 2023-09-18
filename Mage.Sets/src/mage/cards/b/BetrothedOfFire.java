package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeAttachedCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BetrothedOfFire extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("an untapped creature");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BetrothedOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Sacrifice an untapped creature: Enchanted creature gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostEnchantedEffect(2, 0, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ));

        // Sacrifice enchanted creature: Creatures you control get +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(2, 0, Duration.EndOfTurn),
                new SacrificeAttachedCost()
        ));
    }

    private BetrothedOfFire(final BetrothedOfFire card) {
        super(card);
    }

    @Override
    public BetrothedOfFire copy() {
        return new BetrothedOfFire(this);
    }
}
