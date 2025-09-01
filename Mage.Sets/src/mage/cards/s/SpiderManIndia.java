package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderManIndia extends CardImpl {

    public SpiderManIndia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Web-slinging {1}{G}{W}
        this.addAbility(new WebSlingingAbility(this, "{1}{G}{W}"));

        // Pavitr's Seva -- Whenever you cast a creature spell, put a +1/+1 counter on target creature you control. It gains flying until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        );
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("It gains flying until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Pavitr's Seva"));
    }

    private SpiderManIndia(final SpiderManIndia card) {
        super(card);
    }

    @Override
    public SpiderManIndia copy() {
        return new SpiderManIndia(this);
    }
}
