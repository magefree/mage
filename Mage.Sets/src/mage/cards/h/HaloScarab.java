package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaloScarab extends CardImpl {

    public HaloScarab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}, Exile Halo Scarab from your graveyard: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new TreasureToken()), new GenericManaCost(2)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private HaloScarab(final HaloScarab card) {
        super(card);
    }

    @Override
    public HaloScarab copy() {
        return new HaloScarab(this);
    }
}
