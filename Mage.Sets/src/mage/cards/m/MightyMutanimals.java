package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.RedMutantToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class MightyMutanimals extends CardImpl {

    public MightyMutanimals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, create a 2/2 red Mutant creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RedMutantToken())));

        // Alliance -- Whenever another creature you control enters, put a +1/+1 counter on target creature you control.
        Ability ability = new AllianceAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private MightyMutanimals(final MightyMutanimals card) {
        super(card);
    }

    @Override
    public MightyMutanimals copy() {
        return new MightyMutanimals(this);
    }
}
