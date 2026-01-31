
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author Plopman
 */
public final class GuardianIdol extends CardImpl {

    public GuardianIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Guardian Idol enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}: Guardian Idol becomes a 2/2 Golem artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 Golem artifact creature", SubType.GOLEM).withType(CardType.ARTIFACT),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
        new ManaCostsImpl<>("{2}")
    ));
    }

    private GuardianIdol(final GuardianIdol card) {
        super(card);
    }

    @Override
    public GuardianIdol copy() {
        return new GuardianIdol(this);
    }
}
