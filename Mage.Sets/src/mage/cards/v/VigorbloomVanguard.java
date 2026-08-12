package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.PrepareCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class VigorbloomVanguard extends PrepareCard {

    public VigorbloomVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new CardType[]{CardType.CREATURE}, "{1}{G/W}",
            "Seed Suture", new CardType[]{CardType.SORCERY}, "{G/W}"
        );

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Each creature you control with a +1/+1 counter on it has vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            VigilanceAbility.getInstance(),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1)
        ));

        // Seed Suture
        // Sorcery {G/W}
        // Put a +1/+1 counter on target creature. You gain 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private VigorbloomVanguard(final VigorbloomVanguard card) {
        super(card);
    }

    @Override
    public VigorbloomVanguard copy() {
        return new VigorbloomVanguard(this);
    }
}
