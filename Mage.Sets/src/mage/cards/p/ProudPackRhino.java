package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProudPackRhino extends CardImpl {

    public ProudPackRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Proud Pack-Rhino enters the battlefield, choose one --
        // * Put a shield counter on target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.SHIELD.createInstance())
        );
        ability.addTarget(new TargetPermanent());

        // * Proliferate.
        ability.addMode(new Mode(new ProliferateEffect()));
        this.addAbility(ability);
    }

    private ProudPackRhino(final ProudPackRhino card) {
        super(card);
    }

    @Override
    public ProudPackRhino copy() {
        return new ProudPackRhino(this);
    }
}
