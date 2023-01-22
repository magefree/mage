package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SurgicalSkullbomb extends CardImpl {

    public SurgicalSkullbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, Sacrifice Surgical Skullbomb: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {2}{U}, Sacrifice Surgical Skullbomb: Return target creature to its owner's hand. Draw a card. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SurgicalSkullbomb(final SurgicalSkullbomb card) {
        super(card);
    }

    @Override
    public SurgicalSkullbomb copy() {
        return new SurgicalSkullbomb(this);
    }
}
