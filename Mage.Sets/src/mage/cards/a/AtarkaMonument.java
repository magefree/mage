
package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author fireshoes
 */
public final class AtarkaMonument extends CardImpl {

    public AtarkaMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // {4}{R}{G}: Atarka Monument becomes a 4/4 red and green Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 red and green Dragon artifact creature with flying")
                        .withColor("RG")
                        .withSubType(SubType.DRAGON)
                        .withType(CardType.ARTIFACT)
                        .withAbility(FlyingAbility.getInstance()),
                "", Duration.EndOfTurn), new ManaCostsImpl<>("{4}{R}{G}")));
    }

    private AtarkaMonument(final AtarkaMonument card) {
        super(card);
    }

    @Override
    public AtarkaMonument copy() {
        return new AtarkaMonument(this);
    }
}
