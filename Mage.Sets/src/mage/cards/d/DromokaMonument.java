
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class DromokaMonument extends CardImpl {

    public DromokaMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {4}{G}{W}: Dromoka Monument becomes a 4/4 green and white Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    4, 4,
                    "4/4 green and white Dragon artifact creature with flying",
                    SubType.DRAGON
                ).withColor("GW").withAbility(FlyingAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{4}{G}{W}")
        ));
    }

    private DromokaMonument(final DromokaMonument card) {
        super(card);
    }

    @Override
    public DromokaMonument copy() {
        return new DromokaMonument(this);
    }
}
