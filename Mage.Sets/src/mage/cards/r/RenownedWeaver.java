
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.RenownedWeaverSpiderToken;

/**
 *
 * @author LevelX2
 */
public final class RenownedWeaver extends CardImpl {

    public RenownedWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, Sacrifice Renowned Weaver: Create a 1/3 green Spider enchantment creature token with reach.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new RenownedWeaverSpiderToken(), 1), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private RenownedWeaver(final RenownedWeaver card) {
        super(card);
    }

    @Override
    public RenownedWeaver copy() {
        return new RenownedWeaver(this);
    }
}
