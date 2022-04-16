package mage.cards.b;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BootleggersStash extends CardImpl {

    public BootleggersStash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{G}");

        // Lands you control have "{T}: Create a Treasure token."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new SimpleActivatedAbility(
                        new CreateTokenEffect(new TreasureToken()), new TapSourceCost()
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
        )));
    }

    private BootleggersStash(final BootleggersStash card) {
        super(card);
    }

    @Override
    public BootleggersStash copy() {
        return new BootleggersStash(this);
    }
}
