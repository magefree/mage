package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki, North
 */
public final class Riddlesmith extends CardImpl {

    public Riddlesmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an artifact spell, you may draw a card. If you do, discard a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawDiscardControllerEffect(true), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false
        ));
    }

    public Riddlesmith(final Riddlesmith card) {
        super(card);
    }

    @Override
    public Riddlesmith copy() {
        return new Riddlesmith(this);
    }
}
