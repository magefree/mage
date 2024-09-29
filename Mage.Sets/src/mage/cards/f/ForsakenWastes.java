package mage.cards.f;

import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ForsakenWastes extends CardImpl {

    public ForsakenWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.supertype.add(SuperType.WORLD);

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect()));
        
        // At the beginning of each player's upkeep, that player loses 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1),
                TargetController.ANY, false, true));
        
        // Whenever Forsaken Wastes becomes the target of a spell, that spell's controller loses 5 life.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new LoseLifeTargetEffect(5).setText("that spell's controller loses 5 life"),
                StaticFilters.FILTER_SPELL_A, SetTargetPointer.PLAYER, false));
    }

    private ForsakenWastes(final ForsakenWastes card) {
        super(card);
    }

    @Override
    public ForsakenWastes copy() {
        return new ForsakenWastes(this);
    }
}
