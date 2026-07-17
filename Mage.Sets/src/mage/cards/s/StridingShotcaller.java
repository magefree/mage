package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StridingShotcaller extends PrepareCard {

    public StridingShotcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}", "Run the Play", CardType.SORCERY, "{X}{G}{U}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever one or more creatures you control deal combat damage to a player, this creature becomes prepared.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new BecomePreparedSourceEffect(), StaticFilters.FILTER_CONTROLLED_CREATURES
        ));

        // Run the Play
        // Sorcery {X}{G}{U}
        // Put a +1/+1 counter on each of up to X target creatures. Those creatures gain flying until end of turn. Draw a card.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to X target creatures"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("Those creatures gain flying until end of turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellCard().getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private StridingShotcaller(final StridingShotcaller card) {
        super(card);
    }

    @Override
    public StridingShotcaller copy() {
        return new StridingShotcaller(this);
    }
}
