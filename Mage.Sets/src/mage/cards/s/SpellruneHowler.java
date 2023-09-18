package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.NightboundAbility;
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
public final class SpellruneHowler extends CardImpl {

    public SpellruneHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.nightCard = true;

        // Whenever you cast an instant or sorcery spell, Spellrune Howler gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private SpellruneHowler(final SpellruneHowler card) {
        super(card);
    }

    @Override
    public SpellruneHowler copy() {
        return new SpellruneHowler(this);
    }
}
