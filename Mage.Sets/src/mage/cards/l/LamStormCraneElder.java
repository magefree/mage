package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

public final class LamStormCraneElder extends CardImpl {

    public LamStormCraneElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new ProwessAbility());

        // Whenever you cast a noncreature spell, conjure a card named Monastery Mentor onto the battlefield.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ConjureCardEffect("Monastery Mentor", Zone.BATTLEFIELD, 1),
                StaticFilters.FILTER_SPELL_NON_CREATURE, false
        ));
    }

    private LamStormCraneElder(final LamStormCraneElder card) {
        super(card);
    }

    @Override
    public LamStormCraneElder copy() {
        return new LamStormCraneElder(this);
    }
}
