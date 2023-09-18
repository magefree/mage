
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class BobaFett extends CardImpl {

    public BobaFett(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Boba Fett enters the battlefield, put a bounty counter on target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, you may draw two cards and discard two cards.
        this.addAbility(new BountyAbility(new DrawDiscardControllerEffect(2, 2, true)));
    }

    private BobaFett(final BobaFett card) {
        super(card);
    }

    @Override
    public BobaFett copy() {
        return new BobaFett(this);
    }
}
