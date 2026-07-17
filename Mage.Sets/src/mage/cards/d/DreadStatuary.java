

package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DreadStatuary extends CardImpl {

    public DreadStatuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(4, 2, "4/2 Golem artifact creature", SubType.GOLEM)
                    .withType(CardType.ARTIFACT),
                CardType.LAND,
                Duration.EndOfTurn),
            new ManaCostsImpl<>("{4}")
        ));
    }

    private DreadStatuary(final DreadStatuary card) {
        super(card);
    }

    @Override
    public DreadStatuary copy() {
        return new DreadStatuary(this);
    }

}
