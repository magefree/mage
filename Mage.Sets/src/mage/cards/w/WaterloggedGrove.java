package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterloggedGrove extends CardImpl {

    public WaterloggedGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Pay 1 life: Add {G} or {U}.
        Ability ability = new GreenManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
        ability = new BlueManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {1}, {T}, Sacrifice Waterlogged Grove: Draw a card.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private WaterloggedGrove(final WaterloggedGrove card) {
        super(card);
    }

    @Override
    public WaterloggedGrove copy() {
        return new WaterloggedGrove(this);
    }
}
