package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class DrossSkullbomb extends CardImpl {

    public DrossSkullbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, Sacrifice Dross Skullbomb: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);


        // {2}{B}, Sacrifice Dross Skullbomb: Return target creature card from your graveyard to your hand. Draw a card. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private DrossSkullbomb(final DrossSkullbomb card) {
        super(card);
    }

    @Override
    public DrossSkullbomb copy() {
        return new DrossSkullbomb(this);
    }
}
