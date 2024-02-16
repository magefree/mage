
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class GhoulcallersAccomplice extends CardImpl {

    public GhoulcallersAccomplice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{B}, Exile Ghoulcaller's Accomplice from your graveyard: Create a 2/2 black Zombie creature token.
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD, new CreateTokenEffect(new ZombieToken()), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private GhoulcallersAccomplice(final GhoulcallersAccomplice card) {
        super(card);
    }

    @Override
    public GhoulcallersAccomplice copy() {
        return new GhoulcallersAccomplice(this);
    }
}
