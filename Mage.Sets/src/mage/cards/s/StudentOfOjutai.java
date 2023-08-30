package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class StudentOfOjutai extends CardImpl {

    public StudentOfOjutai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, you gain 2 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(2), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));
    }

    private StudentOfOjutai(final StudentOfOjutai card) {
        super(card);
    }

    @Override
    public StudentOfOjutai copy() {
        return new StudentOfOjutai(this);
    }
}
