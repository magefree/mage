
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author ciaccona007
 */
public final class HostileDesert extends CardImpl {

    public HostileDesert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        addAbility(new ColorlessManaAbility());
        // {2}, Exile a land card from your graveyard: Hostile Desert becomes a 3/4 Elemental creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 4, "3/4 Elemental creature", SubType.ELEMENTAL),
                CardType.LAND, Duration.EndOfTurn), new GenericManaCost(2));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterLandCard("land card from your graveyard"))));
        addAbility(ability);
    }

    private HostileDesert(final HostileDesert card) {
        super(card);
    }

    @Override
    public HostileDesert copy() {
        return new HostileDesert(this);
    }
}
