
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author anonymous
 */
public final class HaazdaShieldMate extends CardImpl {

    public HaazdaShieldMate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, sacrifice Haazda Shield Mate unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{W}{W}")), TargetController.YOU, false));
        
        // {W}: The next time a source of your choice would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{W}")));
    }

    private HaazdaShieldMate(final HaazdaShieldMate card) {
        super(card);
    }

    @Override
    public HaazdaShieldMate copy() {
        return new HaazdaShieldMate(this);
    }
}
