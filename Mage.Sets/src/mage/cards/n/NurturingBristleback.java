package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DinosaurVanillaToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NurturingBristleback extends CardImpl {

    public NurturingBristleback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Nurturing Bristleback enters the battlefield, create a 3/3 green Dinosaur creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DinosaurVanillaToken())));

        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private NurturingBristleback(final NurturingBristleback card) {
        super(card);
    }

    @Override
    public NurturingBristleback copy() {
        return new NurturingBristleback(this);
    }
}
