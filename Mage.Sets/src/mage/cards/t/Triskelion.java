package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Triskelion extends CardImpl {

    public Triskelion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Triskelion enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(3)));

        // Remove a +1/+1 counter from Triskelion: Triskelion deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1, "it"),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Triskelion(final Triskelion card) {
        super(card);
    }

    @Override
    public Triskelion copy() {
        return new Triskelion(this);
    }

}
