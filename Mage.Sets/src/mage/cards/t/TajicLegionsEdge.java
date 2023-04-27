package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllNonCombatDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class TajicLegionsEdge extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("other creatures you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TajicLegionsEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Mentor
        this.addAbility(new MentorAbility());

        // Prevent all noncombat damage that would be dealt to other creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new PreventAllNonCombatDamageToAllEffect(
                        Duration.WhileOnBattlefield, filter
                )
        ));

        // {R}{W}: Tajic, Legion's Edge gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        FirstStrikeAbility.getInstance(),
                        Duration.EndOfTurn
                ), new ManaCostsImpl<>("{R}{W}")
        ));
    }

    private TajicLegionsEdge(final TajicLegionsEdge card) {
        super(card);
    }

    @Override
    public TajicLegionsEdge copy() {
        return new TajicLegionsEdge(this);
    }
}
