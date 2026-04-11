package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class DigSiteInventory extends CardImpl {

    public DigSiteInventory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put a +1/+1 counter on target creature you control. It gains vigilance until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
            .setText("It gains vigilance until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{W}")));
    }

    private DigSiteInventory(final DigSiteInventory card) {
        super(card);
    }

    @Override
    public DigSiteInventory copy() {
        return new DigSiteInventory(this);
    }
}
