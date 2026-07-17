
package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author fireshoes
 */
public final class KolaghanMonument extends CardImpl {

    public KolaghanMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // {4}{B}{R}: Kolaghan Monument becomes a 4/4 black and red Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    4, 4, "4/4 black and red Dragon artifact creature with flying", SubType.DRAGON
                ).withColor("BR").withType(CardType.ARTIFACT).withAbility(FlyingAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{4}{B}{R}")
        ));
    }

    private KolaghanMonument(final KolaghanMonument card) {
        super(card);
    }

    @Override
    public KolaghanMonument copy() {
        return new KolaghanMonument(this);
    }
}
