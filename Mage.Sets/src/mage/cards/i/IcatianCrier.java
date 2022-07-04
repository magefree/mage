
package mage.cards.i;

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
import mage.game.permanent.token.CitizenToken;

/**
 *
 * @author fireshoes
 */
public final class IcatianCrier extends CardImpl {

    public IcatianCrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, {tap}, Discard a card: Create two 1/1 white Citizen creature tokens.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new CitizenToken(), 2), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private IcatianCrier(final IcatianCrier card) {
        super(card);
    }

    @Override
    public IcatianCrier copy() {
        return new IcatianCrier(this);
    }
}