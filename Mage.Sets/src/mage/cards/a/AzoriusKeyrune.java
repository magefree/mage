
package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author LevelX2
 */
public final class AzoriusKeyrune extends CardImpl {

    public AzoriusKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // {W}{U}: Azorius Keyrune becomes a 2/2 white and blue Bird artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 white and blue Bird artifact creature with flying")
                        .withColor("WU")
                        .withSubType(SubType.BIRD)
                        .withType(CardType.ARTIFACT)
                        .withAbility(FlyingAbility.getInstance()),
                "", Duration.EndOfTurn), new ManaCostsImpl<>("{W}{U}")));
    }

    private AzoriusKeyrune(final AzoriusKeyrune card) {
        super(card);
    }

    @Override
    public AzoriusKeyrune copy() {
        return new AzoriusKeyrune(this);
    }
}
