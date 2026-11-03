package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 *
 * @author notshauna
 */
public final class WorfIntelligenceContractor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public WorfIntelligenceContractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.SPY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Worf enters, for each opponent, gain control of up to one target creature that player controls with mana value 3 or less until end of turn.
        // Untap those creatures. Until end of turn, they become Warriors in addition to their other types and gain haste.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn)
                .setText ("for each opponent, gain control of up to one target creature with mana value 3 or less that player controls until end of turn")
                .setTargetPointer(new EachTargetPointer()), false);
        ability.addEffect(new AddCardSubTypeTargetEffect(SubType.WARRIOR, Duration.EndOfTurn)
                .setText("Until end of turn, they become Warriors in addition to their other types")
                .setTargetPointer(new EachTargetPointer()));
        ability.addEffect(new UntapTargetEffect()
                .setText("Untap those creatures")
                .setTargetPointer(new EachTargetPointer()));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gain haste")
                .setTargetPointer(new EachTargetPointer()));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);
    }

    private WorfIntelligenceContractor(final WorfIntelligenceContractor card) {
        super(card);
    }

    @Override
    public WorfIntelligenceContractor copy() {
        return new WorfIntelligenceContractor(this);
    }
}