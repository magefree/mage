package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ScaldingTongs extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 4);

    public ScaldingTongs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, if you have three or fewer cards in hand, Scalding Tongs deals 1 damage to target opponent.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(1)).withInterveningIf(condition);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private ScaldingTongs(final ScaldingTongs card) {
        super(card);
    }

    @Override
    public ScaldingTongs copy() {
        return new ScaldingTongs(this);
    }
}
