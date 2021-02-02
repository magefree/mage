
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX
 */
public final class CommuneWithNature extends CardImpl {

    public CommuneWithNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(
                new LookLibraryAndPickControllerEffect(
                        StaticValue.get(5), false, StaticValue.get(1), new FilterCreatureCard("a creature card"), false
                )
        );
    }

    private CommuneWithNature(final CommuneWithNature card) {
        super(card);
    }

    @Override
    public CommuneWithNature copy() {
        return new CommuneWithNature(this);
    }

}
