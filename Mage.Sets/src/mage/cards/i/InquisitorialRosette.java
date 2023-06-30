package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;
import mage.game.permanent.token.WhiteAstartesWarriorToken;

/**
 * @author TheElk801
 */
public final class InquisitorialRosette extends CardImpl {

    public InquisitorialRosette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Inquisition Agents -- Whenever equipped creature attacks, create a 2/2 white Astartes Warrior creature token with vigilance that's attacking. Then attacking creatures gain menace until end of turn.
        Ability ability = new AttacksAttachedTriggeredAbility(
                new CreateTokenEffect(new WhiteAstartesWarriorToken(), 1, false, true)
        );
        ability.addEffect(new GainAbilityAllEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).concatBy("Then"));
        this.addAbility(ability.withFlavorWord("Inquisition Agents"));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private InquisitorialRosette(final InquisitorialRosette card) {
        super(card);
    }

    @Override
    public InquisitorialRosette copy() {
        return new InquisitorialRosette(this);
    }
}
