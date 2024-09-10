package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrazcaPuzzleDoor extends CardImpl {

    public OrazcaPuzzleDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        // {1}, {T}, Sacrifice Orazca Puzzle-Door: Look at the top two cards of your library. Put one of those cards into your hand and the other into your graveyard.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                2, 1, PutCards.HAND, PutCards.GRAVEYARD
        ), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OrazcaPuzzleDoor(final OrazcaPuzzleDoor card) {
        super(card);
    }

    @Override
    public OrazcaPuzzleDoor copy() {
        return new OrazcaPuzzleDoor(this);
    }
}
