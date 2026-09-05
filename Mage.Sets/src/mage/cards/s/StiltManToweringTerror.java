package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.common.SimpleStaticAbility;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class StiltManToweringTerror extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VILLAIN, "Villains");
    private static final FilterPermanent filter2 = new FilterNonlandPermanent("noncreature, nonland permanent");

    static {
        filter2.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public StiltManToweringTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever one or more Villains you control deal combat damage to a player, gain control of target noncreature, nonland permanent that player controls until the end of your next turn. It gains "This permanent can't be sacrificed" until the end of your next turn.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
            Zone.BATTLEFIELD,
            new GainControlTargetEffect(Duration.UntilEndOfYourNextTurn, true)
                .setText("gain control of target noncreature, nonland permanent that player controls until the end of your next turn"),
            filter, SetTargetPointer.PLAYER, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
            new SimpleStaticAbility(new CantBeSacrificedSourceEffect()),
            Duration.UntilEndOfYourNextTurn,
            "It gains \"This permanent can't be sacrificed\" until the end of your next turn"
        ));
        ability.addTarget(new TargetPermanent(filter2));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private StiltManToweringTerror(final StiltManToweringTerror card) {
        super(card);
    }

    @Override
    public StiltManToweringTerror copy() {
        return new StiltManToweringTerror(this);
    }
}
