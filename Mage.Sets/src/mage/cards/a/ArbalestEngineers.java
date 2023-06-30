package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ArbalestEngineers extends CardImpl {

    public ArbalestEngineers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Arbalest Engineers etners the battlefield, choose one --
        // * Arbalest Engineers deals 1 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetAnyTarget());

        // * Put a +1/+1 on target creature. It gains trample and haste until end of turn.
        Mode mode = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        mode.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("It gains trample"));
        mode.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and haste until end of turn."));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);

        // * Create a taped Powerstone token.
        ability.addMode(new Mode(new CreateTokenEffect(new PowerstoneToken(), 1, true)));
        this.addAbility(ability);
    }

    private ArbalestEngineers(final ArbalestEngineers card) {
        super(card);
    }

    @Override
    public ArbalestEngineers copy() {
        return new ArbalestEngineers(this);
    }
}
