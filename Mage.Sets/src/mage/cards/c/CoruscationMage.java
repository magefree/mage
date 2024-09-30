package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.OffspringAbility;
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
public final class CoruscationMage extends CardImpl {

    public CoruscationMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // Whenever you cast a noncreature spell, this creature deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT, "this creature"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private CoruscationMage(final CoruscationMage card) {
        super(card);
    }

    @Override
    public CoruscationMage copy() {
        return new CoruscationMage(this);
    }
}
