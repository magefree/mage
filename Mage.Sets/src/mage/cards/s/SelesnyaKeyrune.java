
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author LevelX2
 */
public final class SelesnyaKeyrune extends CardImpl {

    public SelesnyaKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {G}{W}: Selesnya Keyrune becomes a 3/3 green and white Wolf artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 green and white Wolf artifact creature", SubType.WOLF)
                    .withColor("GW").withType(CardType.ARTIFACT),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{G}{W}")
        ));
    }

    private SelesnyaKeyrune(final SelesnyaKeyrune card) {
        super(card);
    }

    @Override
    public SelesnyaKeyrune copy() {
        return new SelesnyaKeyrune(this);
    }
}
