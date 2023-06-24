package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosTerminatorLord extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChaosTerminatorLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lord of Chaos -- At the beginning of combat on your turn, another target creature you control gains double strike until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Lord of Chaos"));
    }

    private ChaosTerminatorLord(final ChaosTerminatorLord card) {
        super(card);
    }

    @Override
    public ChaosTerminatorLord copy() {
        return new ChaosTerminatorLord(this);
    }
}
