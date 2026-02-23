package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BramblebackBrute extends CardImpl {

    public BramblebackBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(2)));

        // {1}{R}, Remove a counter from this creature: Target creature can't block this turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new RemoveCountersSourceCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BramblebackBrute(final BramblebackBrute card) {
        super(card);
    }

    @Override
    public BramblebackBrute copy() {
        return new BramblebackBrute(this);
    }
}
