package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnragedFlamecaster extends CardImpl {

    public EnragedFlamecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you cast a spell with mana value 4 or greater, this creature deals 2 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_MV_4_OR_GREATER, false
        ));
    }

    private EnragedFlamecaster(final EnragedFlamecaster card) {
        super(card);
    }

    @Override
    public EnragedFlamecaster copy() {
        return new EnragedFlamecaster(this);
    }
}
