package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnhartGeist extends CardImpl {

    public DawnhartGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast an enchantment spell, you gain 2 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(2), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false));
    }

    private DawnhartGeist(final DawnhartGeist card) {
        super(card);
    }

    @Override
    public DawnhartGeist copy() {
        return new DawnhartGeist(this);
    }
}
