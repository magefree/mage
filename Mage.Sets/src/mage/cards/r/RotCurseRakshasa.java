package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DecayedAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RotCurseRakshasa extends CardImpl {

    public RotCurseRakshasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Decayed
        this.addAbility(new DecayedAbility());

        // Renew -- {X}{B}{B}, Exile this card from your graveyard: Put a decayed counter on each of X target creatures. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new AddCountersTargetEffect(CounterType.DECAYED.createInstance(1))
                        .setText("put a decayed counter on each of X target creatures"),
                new ManaCostsImpl<>("{X}{B}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new TargetsCountAdjuster(GetXValue.instance));
        this.addAbility(ability.setAbilityWord(AbilityWord.RENEW));
    }

    private RotCurseRakshasa(final RotCurseRakshasa card) {
        super(card);
    }

    @Override
    public RotCurseRakshasa copy() {
        return new RotCurseRakshasa(this);
    }
}
