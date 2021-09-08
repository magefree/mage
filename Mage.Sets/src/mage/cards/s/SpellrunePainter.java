package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellrunePainter extends CardImpl {

    public SpellrunePainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.s.SpellruneHowler.class;

        // Whenever you cast an instant or sorcery spell, Spellrune Painter gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Daybound
        this.addAbility(DayboundAbility.getInstance());
    }

    private SpellrunePainter(final SpellrunePainter card) {
        super(card);
    }

    @Override
    public SpellrunePainter copy() {
        return new SpellrunePainter(this);
    }
}
