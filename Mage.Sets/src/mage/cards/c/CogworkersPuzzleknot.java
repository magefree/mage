
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.ServoToken;

/**
 *
 * @author emerald000
 */
public final class CogworkersPuzzleknot extends CardImpl {

    public CogworkersPuzzleknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Cogworker's Puzzleknot enters the battlefield, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ServoToken())));

        // {1}{W}, Sacrifice Cogworker's Puzzleknot: Create a 1/1 colorless Servo artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ServoToken()), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CogworkersPuzzleknot(final CogworkersPuzzleknot card) {
        super(card);
    }

    @Override
    public CogworkersPuzzleknot copy() {
        return new CogworkersPuzzleknot(this);
    }
}
