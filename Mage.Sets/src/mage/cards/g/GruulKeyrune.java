
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LevelX2
 */
public final class GruulKeyrune extends CardImpl {

    public GruulKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // {R}{G}: Gruul Keyrune becomes a 3/2 red and green Beast artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(3, 2, "3/2 red and green Beast artifact creature with trample", SubType.BEAST)
                    .withColor("RG").withType(CardType.ARTIFACT).withAbility(TrampleAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{R}{G}")
        ));
    }

    private GruulKeyrune(final GruulKeyrune card) {
        super(card);
    }

    @Override
    public GruulKeyrune copy() {
        return new GruulKeyrune(this);
    }
}
