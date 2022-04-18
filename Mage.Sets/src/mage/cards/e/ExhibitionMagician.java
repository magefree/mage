package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class ExhibitionMagician extends CardImpl {

    public ExhibitionMagician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Exhibition Magician enters the battlefield, choose one —
        // • Create a 1/1 green and white Citizen creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CitizenGreenWhiteToken()));

        // • Create a Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())));
        this.addAbility(ability);
    }

    private ExhibitionMagician(final ExhibitionMagician card) {
        super(card);
    }

    @Override
    public ExhibitionMagician copy() {
        return new ExhibitionMagician(this);
    }
}
