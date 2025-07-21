package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.WarpAbility;
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
public final class WeftstalkerArdent extends CardImpl {

    public WeftstalkerArdent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DRIX);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature or artifact you control enters, this creature deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT
        ));

        // Warp {R}
        this.addAbility(new WarpAbility(this, "{R}"));
    }

    private WeftstalkerArdent(final WeftstalkerArdent card) {
        super(card);
    }

    @Override
    public WeftstalkerArdent copy() {
        return new WeftstalkerArdent(this);
    }
}
