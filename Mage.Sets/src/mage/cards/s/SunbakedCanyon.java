package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunbakedCanyon extends CardImpl {

    public SunbakedCanyon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Pay 1 life: Add {R} or {W}.
        Ability ability = new RedManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
        ability = new WhiteManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {1}, {T}, Sacrifice Sunbaked Canyon: Draw a card.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SunbakedCanyon(final SunbakedCanyon card) {
        super(card);
    }

    @Override
    public SunbakedCanyon copy() {
        return new SunbakedCanyon(this);
    }
}
