package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExaltedFlamerOfTzeentch extends CardImpl {

    public ExaltedFlamerOfTzeentch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Sorcerous Inspiration -- At the beginning of your upkeep, return an instant or sorcery card at random from your graveyard to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardAtRandomEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, Zone.HAND), TargetController.YOU, false
        ).withFlavorWord("Sorcerous Inspiration"));

        // Fire of Tzeentch -- Whenever you cast an instant or sorcery spell, Exalted Flamer of Tzeentch deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ).withFlavorWord("Fire of Tzeentch"));
    }

    private ExaltedFlamerOfTzeentch(final ExaltedFlamerOfTzeentch card) {
        super(card);
    }

    @Override
    public ExaltedFlamerOfTzeentch copy() {
        return new ExaltedFlamerOfTzeentch(this);
    }
}
