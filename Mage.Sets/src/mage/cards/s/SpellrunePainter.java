package mage.cards.s;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellrunePainter extends TransformingDoubleFacedCard {

    public SpellrunePainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF}, "{2}{R}",
                "Spellrune Howler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Spellrune Painter
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever you cast an instant or sorcery spell, Spellrune Painter gets +1/+1 until end of turn.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Spellrune Howler
        this.getRightHalfCard().setPT(3, 4);

        // Whenever you cast an instant or sorcery spell, Spellrune Howler gets +2/+2 until end of turn.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private SpellrunePainter(final SpellrunePainter card) {
        super(card);
    }

    @Override
    public SpellrunePainter copy() {
        return new SpellrunePainter(this);
    }
}
