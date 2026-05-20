package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KirolHistoryBuff extends PrepareCard {

    public KirolHistoryBuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}", "Pack a Punch", new CardType[]{CardType.SORCERY}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever one or more cards leave your graveyard, Kirol becomes prepared.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new BecomePreparedSourceEffect()));

        // Pack a Punch
        // Sorcery {1}{R}{W}
        // Mill a card. Put two +1/+1 counters on target creature. It gains trample until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new MillCardsControllerEffect(1));
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).withTargetDescription("It"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KirolHistoryBuff(final KirolHistoryBuff card) {
        super(card);
    }

    @Override
    public KirolHistoryBuff copy() {
        return new KirolHistoryBuff(this);
    }
}
