package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieryIslet extends CardImpl {

    public FieryIslet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Pay 1 life: Add {U} or {R}.
        Ability ability = new BlueManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
        ability = new RedManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {1}, {T}, Sacrifice Fiery Islet: Draw a card.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FieryIslet(final FieryIslet card) {
        super(card);
    }

    @Override
    public FieryIslet copy() {
        return new FieryIslet(this);
    }
}
