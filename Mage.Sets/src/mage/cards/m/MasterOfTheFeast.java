
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class MasterOfTheFeast extends CardImpl {

    public MasterOfTheFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, each opponent draws a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardAllEffect(1, TargetController.OPPONENT), TargetController.YOU, false));
    }

    private MasterOfTheFeast(final MasterOfTheFeast card) {
        super(card);
    }

    @Override
    public MasterOfTheFeast copy() {
        return new MasterOfTheFeast(this);
    }
}
