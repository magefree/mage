package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FogwellsGym extends CardImpl {

    public FogwellsGym(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {R}. This land deals 1 damage to you.
        Ability redManaAbility = new RedManaAbility();
        redManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(redManaAbility);

        // {2}{R}, {T}, Discard a card: Draw a card.
        Ability ability2 = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{2}{R}")
        );
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new DiscardCardCost());
        this.addAbility(ability2);
    }

    private FogwellsGym(final FogwellsGym card) {
        super(card);
    }

    @Override
    public FogwellsGym copy() {
        return new FogwellsGym(this);
    }
}
