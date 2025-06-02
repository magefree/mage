package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchmageOfRunes extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery spells");

    public ArchmageOfRunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private ArchmageOfRunes(final ArchmageOfRunes card) {
        super(card);
    }

    @Override
    public ArchmageOfRunes copy() {
        return new ArchmageOfRunes(this);
    }
}
