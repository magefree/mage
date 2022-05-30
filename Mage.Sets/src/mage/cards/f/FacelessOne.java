package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.CommanderChooseColorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FacelessOne extends CardImpl {

    public FacelessOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If Faceless One is your commander, choose a color before the game begins. Faceless One is the chosen color.
        this.addAbility(new CommanderChooseColorAbility());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private FacelessOne(final FacelessOne card) {
        super(card);
    }

    @Override
    public FacelessOne copy() {
        return new FacelessOne(this);
    }
}
