package mage.cards.s;

import mage.abilities.common.AttacksOpponentWithMostLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordCoastSailor extends CardImpl {

    public SwordCoastSailor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature attacks a player, if no opponent has more life than that player, this creature can't be blocked this turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new AttacksOpponentWithMostLifeTriggeredAbility(
                        new CantBeBlockedSourceEffect(Duration.EndOfTurn)
                                .setText("this creature can't be blocked this turn"),
                        false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private SwordCoastSailor(final SwordCoastSailor card) {
        super(card);
    }

    @Override
    public SwordCoastSailor copy() {
        return new SwordCoastSailor(this);
    }
}
