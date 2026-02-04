
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
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
public final class SimicKeyrune extends CardImpl {

    public SimicKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // {G}{U}: Simic Keyrune becomes a 2/3 green and blue Crab artifact creature with hexproof until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(2, 3, "2/3 green and blue Crab artifact creature with hexproof", SubType.CRAB)
                    .withColor("GU").withType(CardType.ARTIFACT).withAbility(HexproofAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{G}{U}")
        ));
    }

    private SimicKeyrune(final SimicKeyrune card) {
        super(card);
    }

    @Override
    public SimicKeyrune copy() {
        return new SimicKeyrune(this);
    }
}
