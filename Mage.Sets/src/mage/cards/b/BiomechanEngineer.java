package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.LanderToken;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiomechanEngineer extends CardImpl {

    public BiomechanEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, create a Lander token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new LanderToken())));

        // {8}: Draw two cards and create a 2/2 colorless Robot artifact creature token.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2), new GenericManaCost(8));
        ability.addEffect(new CreateTokenEffect(new RobotToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private BiomechanEngineer(final BiomechanEngineer card) {
        super(card);
    }

    @Override
    public BiomechanEngineer copy() {
        return new BiomechanEngineer(this);
    }
}
