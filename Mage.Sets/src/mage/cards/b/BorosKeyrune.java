
package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.mana.RedManaAbility;
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
public final class BorosKeyrune extends CardImpl {

    public BorosKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {R}{W}: Boros Keyrune becomes a 1/1 red and white Soldier artifact creature with double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(1, 1, "1/1 red and white Soldier artifact creature with double strike")
                        .withColor("RW")
                        .withSubType(SubType.SOLDIER)
                        .withType(CardType.ARTIFACT)
                        .withAbility(DoubleStrikeAbility.getInstance()),
                "", Duration.EndOfTurn), new ManaCostsImpl<>("{R}{W}")));
    }

    private BorosKeyrune(final BorosKeyrune card) {
        super(card);
    }

    @Override
    public BorosKeyrune copy() {
        return new BorosKeyrune(this);
    }
}
