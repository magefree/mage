
package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class JalumTome extends CardImpl {

    public JalumTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.BOOK);

        // {2}, {tap}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(new DrawDiscardControllerEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JalumTome(final JalumTome card) {
        super(card);
    }

    @Override
    public JalumTome copy() {
        return new JalumTome(this);
    }
}
