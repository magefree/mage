package mage.cards.r;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RallyTheRanks extends CardImpl {

    public RallyTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // As Rally the Ranks enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(
                1, 1, Duration.WhileOnBattlefield, false
        )));
    }

    private RallyTheRanks(final RallyTheRanks card) {
        super(card);
    }

    @Override
    public RallyTheRanks copy() {
        return new RallyTheRanks(this);
    }
}
