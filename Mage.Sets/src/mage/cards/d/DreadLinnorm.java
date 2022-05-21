package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadLinnorm extends AdventureCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public DreadLinnorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{6}{G}", "Scale Deflection", "{3}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Dread Linnorm can't be blocked by creatures with power 3 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Scale Deflection
        // Put two +1/+1 counters on target creature and untap it. It gains hexproof until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellCard().getSpellAbility().addEffect(new UntapTargetEffect().setText("and untap it"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()).setText("It gains hexproof until end of turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DreadLinnorm(final DreadLinnorm card) {
        super(card);
    }

    @Override
    public DreadLinnorm copy() {
        return new DreadLinnorm(this);
    }
}
