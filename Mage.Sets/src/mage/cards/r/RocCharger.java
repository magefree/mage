package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RocCharger extends CardImpl {

    static final FilterAttackingCreature filter
            = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public RocCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Roc Charger attacks, target attacking creature without flying gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new GainAbilityTargetEffect(
                        FlyingAbility.getInstance(),
                        Duration.EndOfTurn
                ), false
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        addAbility(ability);
    }

    private RocCharger(final RocCharger card) {
        super(card);
    }

    @Override
    public RocCharger copy() {
        return new RocCharger(this);
    }
}
