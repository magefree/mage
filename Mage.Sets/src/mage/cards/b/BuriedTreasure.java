package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BuriedTreasure extends CardImpl {

    public BuriedTreasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.TREASURE);

        // {T}, Sacrifice Buried Treasure: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {5}, Exile Buried Treasure from your graveyard: Discover 5. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new DiscoverEffect(5), new GenericManaCost(5)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private BuriedTreasure(final BuriedTreasure card) {
        super(card);
    }

    @Override
    public BuriedTreasure copy() {
        return new BuriedTreasure(this);
    }
}
