package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RubblebeltMaverick extends CardImpl {

    public RubblebeltMaverick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Rubblebelt Maverick enters the battlefield, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));

        // {G}, Exile Rubblebelt Maverick from your graveyard: Put a +1/+1 counter on target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RubblebeltMaverick(final RubblebeltMaverick card) {
        super(card);
    }

    @Override
    public RubblebeltMaverick copy() {
        return new RubblebeltMaverick(this);
    }
}
