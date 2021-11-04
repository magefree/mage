package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
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
public final class KessigFlamebreather extends CardImpl {

    public KessigFlamebreather(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, Kessig Flamebreather deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private KessigFlamebreather(final KessigFlamebreather card) {
        super(card);
    }

    @Override
    public KessigFlamebreather copy() {
        return new KessigFlamebreather(this);
    }
}
