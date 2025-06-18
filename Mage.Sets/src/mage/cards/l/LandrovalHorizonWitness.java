package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LandrovalHorizonWitness extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }
    public LandrovalHorizonWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever two or more creatures you control attack a player, target attacking creature without flying gains flying until end of turn.
        Ability ability = new AttacksPlayerWithCreaturesTriggeredAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                2, StaticFilters.FILTER_CONTROLLED_CREATURES, SetTargetPointer.NONE, false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LandrovalHorizonWitness(final LandrovalHorizonWitness card) {
        super(card);
    }

    @Override
    public LandrovalHorizonWitness copy() {
        return new LandrovalHorizonWitness(this);
    }
}
