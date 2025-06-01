package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClashOfTheEikons extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAGA);

    public ClashOfTheEikons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one or more --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // * Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // * Remove a lore counter from target Saga you control.
        this.getSpellAbility().addMode(new Mode(new RemoveCounterTargetEffect(CounterType.LORE.createInstance()))
                .addTarget(new TargetPermanent(filter)));

        // * Put a lore counter on target Saga you control.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(CounterType.LORE.createInstance()))
                .addTarget(new TargetPermanent(filter)));
    }

    private ClashOfTheEikons(final ClashOfTheEikons card) {
        super(card);
    }

    @Override
    public ClashOfTheEikons copy() {
        return new ClashOfTheEikons(this);
    }
}
