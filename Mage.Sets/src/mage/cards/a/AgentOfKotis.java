package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentOfKotis extends CardImpl {

    public AgentOfKotis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Renew -- {3}{U}, Exile this card from your graveyard: Put two +1/+1 counters on target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{3}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.RENEW));
    }

    private AgentOfKotis(final AgentOfKotis card) {
        super(card);
    }

    @Override
    public AgentOfKotis copy() {
        return new AgentOfKotis(this);
    }
}
