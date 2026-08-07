package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.effects.keyword.AmassEffect;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class BothersomeNoisemaker extends CardImpl {

    public BothersomeNoisemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, amass Goblins 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AmassEffect(1, SubType.GOBLIN),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private BothersomeNoisemaker(final BothersomeNoisemaker card) {
        super(card);
    }

    @Override
    public BothersomeNoisemaker copy() {
        return new BothersomeNoisemaker(this);
    }
}
