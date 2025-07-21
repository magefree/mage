package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BowOfNylea extends CardImpl {

    public BowOfNylea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.ARTIFACT}, "{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);

        // Attacking creatures you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_ATTACKING_CREATURES, false)));

        // {1}{G}, {T}: Choose one - Put a +1/+1 counter on target creature;
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        // or Bow of Nylea deals 2 damage to target creature with flying;
        Mode mode = new Mode(new DamageTargetEffect(2));
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        ability.addMode(mode);
        // or you gain 3 life;
        mode = new Mode(new GainLifeEffect(3));
        ability.addMode(mode);
        // or put up to four target cards from your graveyard on the bottom of your library in any order.
        mode = new Mode(new PutOnLibraryTargetEffect(false, "put up to four target cards from your graveyard on the bottom of your library in any order"));
        mode.addTarget(new TargetCardInYourGraveyard(0, 4, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private BowOfNylea(final BowOfNylea card) {
        super(card);
    }

    @Override
    public BowOfNylea copy() {
        return new BowOfNylea(this);
    }
}
