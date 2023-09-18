package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GryffwingCavalry extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public GryffwingCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Training
        this.addAbility(new TrainingAbility());

        // Whenever Gryffwing Cavalry attacks, you may pay {1}{W}. If you do, target attacking creature without flying gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainAbilityTargetEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ), new ManaCostsImpl<>("{1}{W}")
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GryffwingCavalry(final GryffwingCavalry card) {
        super(card);
    }

    @Override
    public GryffwingCavalry copy() {
        return new GryffwingCavalry(this);
    }
}
