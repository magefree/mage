package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;

/**
 * @author xenohedron
 */
public final class MildManneredLibrarian extends CardImpl {

    public MildManneredLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{G}: Mild-Mannered Librarian becomes a Werewolf. Put two +1/+1 counters on it and you draw a card. Activate only once.
        Ability ability = new ActivateOncePerGameActivatedAbility(Zone.BATTLEFIELD, new AddCardSubTypeSourceEffect(Duration.Custom, SubType.WEREWOLF),
                new ManaCostsImpl<>("{3}{G}"), TimingRule.INSTANT);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).setText("Put two +1/+1 counters on it"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        this.addAbility(ability);
    }

    private MildManneredLibrarian(final MildManneredLibrarian card) {
        super(card);
    }

    @Override
    public MildManneredLibrarian copy() {
        return new MildManneredLibrarian(this);
    }
}
