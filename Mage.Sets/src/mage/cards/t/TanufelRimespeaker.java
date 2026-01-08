package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TanufelRimespeaker extends CardImpl {

    public TanufelRimespeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a spell with mana value 4 or greater, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_MV_4_OR_GREATER, false
        ));
    }

    private TanufelRimespeaker(final TanufelRimespeaker card) {
        super(card);
    }

    @Override
    public TanufelRimespeaker copy() {
        return new TanufelRimespeaker(this);
    }
}
