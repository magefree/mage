package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AbstractPaintmage extends CardImpl {

    public AbstractPaintmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U/R}{R}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your first main phase, add {U}{R}. Spend this mana only to cast instant and sorcery spells.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
            new AddConditionalManaEffect(
                new Mana(0, 1, 0, 1, 0, 0, 0, 0),
                new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELLS_INSTANT_OR_SORCERY)
            )
        ));
    }

    private AbstractPaintmage(final AbstractPaintmage card) {
        super(card);
    }

    @Override
    public AbstractPaintmage copy() {
        return new AbstractPaintmage(this);
    }
}
