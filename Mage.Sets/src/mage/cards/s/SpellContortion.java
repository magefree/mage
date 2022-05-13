
package mage.cards.s;

import java.util.UUID;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 * @author jeffwadsworth
 */
public final class SpellContortion extends CardImpl {

    public SpellContortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");


        // Multikicker {1}{U}
        this.addAbility(new MultikickerAbility("{1}{U}"));

        // Counter target spell unless its controller pays {2}. Draw a card for each time Spell Contortion was kicked.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(MultikickerCount.instance).setText("draw a card for each time {this} was kicked"));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private SpellContortion(final SpellContortion card) {
        super(card);
    }

    @Override
    public SpellContortion copy() {
        return new SpellContortion(this);
    }
}
