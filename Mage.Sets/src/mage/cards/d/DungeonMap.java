package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DungeonMap extends CardImpl {

    public DungeonMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Venture into the dungeon. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new VentureIntoTheDungeonEffect(), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DungeonMap(final DungeonMap card) {
        super(card);
    }

    @Override
    public DungeonMap copy() {
        return new DungeonMap(this);
    }
}
