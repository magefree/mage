
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BeastToken;

/**
 *
 * @author Plopman
 */
public final class BringerOfTheGreenDawn extends CardImpl {

    public BringerOfTheGreenDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{G}{G}");
        this.subtype.add(SubType.BRINGER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may pay {W}{U}{B}{R}{G} rather than pay Bringer of the Green Dawn's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep,s you may create a 3/3 green Beast creature token.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new BeastToken()), true);
        this.addAbility(ability);
    }

    private BringerOfTheGreenDawn(final BringerOfTheGreenDawn card) {
        super(card);
    }

    @Override
    public BringerOfTheGreenDawn copy() {
        return new BringerOfTheGreenDawn(this);
    }
}
