package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class CharmbreakerDevils extends CardImpl {

    public CharmbreakerDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, return an instant or sorcery card at random from your graveyard to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardAtRandomEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, Zone.HAND),
                TargetController.YOU, false
        ));

        // Whenever you cast an instant or sorcery spell, Charmbreaker Devils gets +4/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(4, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private CharmbreakerDevils(final CharmbreakerDevils card) {
        super(card);
    }

    @Override
    public CharmbreakerDevils copy() {
        return new CharmbreakerDevils(this);
    }
}
