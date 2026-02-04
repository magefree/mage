
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class ThunderTotem extends CardImpl {

    public ThunderTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{W}{W}: Thunder Totem becomes a 2/2 white Spirit artifact creature with flying and first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 white Spirit artifact creature with flying and first strike", SubType.SPIRIT)
                    .withColor("W")
                    .withType(CardType.ARTIFACT)
                    .withAbility(FlyingAbility.getInstance())
                    .withAbility(FirstStrikeAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{1}{W}{W}")
        ));
    }

    private ThunderTotem(final ThunderTotem card) {
        super(card);
    }

    @Override
    public ThunderTotem copy() {
        return new ThunderTotem(this);
    }
}
