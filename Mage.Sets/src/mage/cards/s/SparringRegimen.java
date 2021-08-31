package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparringRegimen extends CardImpl {

    public SparringRegimen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Sparring Regimen enters the battlefield, learn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LearnEffect())
                .addHint(OpenSideboardHint.instance));

        // Whenever you attack, put a +1/+1 counter on target attacking creature and untap it.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1
        );
        ability.addEffect(new UntapTargetEffect().setText("and untap it"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private SparringRegimen(final SparringRegimen card) {
        super(card);
    }

    @Override
    public SparringRegimen copy() {
        return new SparringRegimen(this);
    }
}
