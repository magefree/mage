package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class JayemdaeTome extends CardImpl {

    public JayemdaeTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.BOOK);

        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JayemdaeTome(final JayemdaeTome card) {
        super(card);
    }

    @Override
    public JayemdaeTome copy() {
        return new JayemdaeTome(this);
    }
}
