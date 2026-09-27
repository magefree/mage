package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CryotheoryAdept extends CardImpl {

    public CryotheoryAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // {3}{U}, Exile this card from your graveyard: Tap target creature and put a stun counter on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            Zone.GRAVEYARD, 
            new TapTargetEffect(), 
            new ManaCostsImpl<>("{3}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CryotheoryAdept(final CryotheoryAdept card) {
        super(card);
    }

    @Override
    public CryotheoryAdept copy() {
        return new CryotheoryAdept(this);
    }
}
