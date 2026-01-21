package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnarlbarkElm extends CardImpl {

    public GnarlbarkElm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(2)));

        // {2}{B}, Remove two counters from this creature: Target creature gets -2/-2 until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(-2, -2), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new RemoveCountersSourceCost(2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GnarlbarkElm(final GnarlbarkElm card) {
        super(card);
    }

    @Override
    public GnarlbarkElm copy() {
        return new GnarlbarkElm(this);
    }
}
