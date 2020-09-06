
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author cbt33
 */
public final class NutCollector extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("squirrel");

    static {
        filter.add(SubType.SQUIRREL.getPredicate());
    }

    public NutCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may create a 1/1 green Squirrel creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SquirrelToken()), TargetController.YOU, true));
        // Threshold - Squirrel creatures get +2/+2 as long as seven or more cards are in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false),
            new CardsInControllerGraveyardCondition(7), "Squirrel creatures get +2/+2 as long as seven or more cards are in your graveyard"));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public NutCollector(final NutCollector card) {
        super(card);
    }

    @Override
    public NutCollector copy() {
        return new NutCollector(this);
    }
}
