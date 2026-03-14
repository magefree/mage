
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author LevelX2
 */
public final class RakdosKeyrune extends CardImpl {

    public RakdosKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // {B}{R}: Rakdos Keyrune becomes a 3/1 black and red Devil artifact creature with first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(3, 1, "3/1 black and red Devil artifact creature with first strike", SubType.DEVIL)
                    .withColor("BR").withType(CardType.ARTIFACT).withAbility(FirstStrikeAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{B}{R}")
        ));
    }

    private RakdosKeyrune(final RakdosKeyrune card) {
        super(card);
    }

    @Override
    public RakdosKeyrune copy() {
        return new RakdosKeyrune(this);
    }
}
