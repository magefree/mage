package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class AcademyWall extends CardImpl {

    public AcademyWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, you may draw a card. If you do, discard a card. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawDiscardControllerEffect(true),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false
        ).setTriggersOnce(true));
    }

    private AcademyWall(final AcademyWall card) {
        super(card);
    }

    @Override
    public AcademyWall copy() {
        return new AcademyWall(this);
    }
}
