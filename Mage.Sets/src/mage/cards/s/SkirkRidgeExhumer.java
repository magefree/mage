
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.FesteringGoblinToken;

/**
 *
 * @author LoneFox
 */
public final class SkirkRidgeExhumer extends CardImpl {

    public SkirkRidgeExhumer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}, Discard a card: Create a 1/1 black Zombie Goblin creature token named Festering Goblin. It has "When Festering Goblin dies, target creature gets -1/-1 until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new FesteringGoblinToken()), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private SkirkRidgeExhumer(final SkirkRidgeExhumer card) {
        super(card);
    }

    @Override
    public SkirkRidgeExhumer copy() {
        return new SkirkRidgeExhumer(this);
    }
}
