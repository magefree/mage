package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillOfTheSultai extends CardImpl {

    public WillOfTheSultai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Target player mills three cards. Return all land cards from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_LANDS, true));

        // * Put X +1/+1 counters on target creature, where X is the number of lands you control. It gains trample until end of turn.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(0), LandsYouControlCount.instance
        )).addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("it gains trample until end of turn")).addTarget(new TargetCreaturePermanent()));
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private WillOfTheSultai(final WillOfTheSultai card) {
        super(card);
    }

    @Override
    public WillOfTheSultai copy() {
        return new WillOfTheSultai(this);
    }
}
