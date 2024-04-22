package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author MarcoMarin, xenohedron
 */
public final class GoblinKites extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("creature you control with toughness 2 or less");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public GoblinKites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // {R}: Target creature you control with toughness 2 or less gains flying until end of turn. Flip a coin at the beginning of the next end step. If you lose the flip, sacrifice that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(filter));
        Effect effect = new FlipCoinEffect(null, new SacrificeTargetEffect().setText("sacrifice that creature"))
                .setText("Flip a coin at the beginning of the next end step. If you lose the flip, sacrifice that creature");
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect)
                .setTriggerPhrase("")));
        this.addAbility(ability);
    }

    private GoblinKites(final GoblinKites card) {
        super(card);
    }

    @Override
    public GoblinKites copy() {
        return new GoblinKites(this);
    }
}
