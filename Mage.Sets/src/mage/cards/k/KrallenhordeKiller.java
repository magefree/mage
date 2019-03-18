
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class KrallenhordeKiller extends CardImpl {

    public KrallenhordeKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.nightCard = true;
        this.transformable = true;

        // {3}{G}: Krallenhorde Killer gets +4/+4 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Duration.EndOfTurn), new ManaCostsImpl("{3}{G}")));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Killer.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public KrallenhordeKiller(final KrallenhordeKiller card) {
        super(card);
    }

    @Override
    public KrallenhordeKiller copy() {
        return new KrallenhordeKiller(this);
    }
}
