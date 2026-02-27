
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author LevelX2
 */
public final class GolgariKeyrune extends CardImpl {

    public GolgariKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // {B}{G}: Golgari Keyrune becomes a 2/2 black and green Insect artifact creature with deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 black and green Insect artifact creature with deathtouch", SubType.INSECT)
                    .withColor("BG").withType(CardType.ARTIFACT).withAbility(DeathtouchAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{B}{G}")
        ));
    }

    private GolgariKeyrune(final GolgariKeyrune card) {
        super(card);
    }

    @Override
    public GolgariKeyrune copy() {
        return new GolgariKeyrune(this);
    }
}
