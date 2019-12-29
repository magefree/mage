package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrypticCaves extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledLandPermanent("you control five or more lands");
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4);

    public CrypticCaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice Cryptic Caves: Draw a card. Activate this ability only if you control five or more lands.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new GenericManaCost(1), condition
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CrypticCaves(final CrypticCaves card) {
        super(card);
    }

    @Override
    public CrypticCaves copy() {
        return new CrypticCaves(this);
    }
}
