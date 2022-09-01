
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SoramaroFirstToDream extends CardImpl {

    public SoramaroFirstToDream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Soramaro, First to Dream's power and toughness are each equal to the number of cards in your hand.
         DynamicValue xValue= CardsInControllerHandCount.instance;
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame)));

        // {4}, Return a land you control to its owner's hand: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1), new GenericManaCost(4));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        this.addAbility(ability);

    }

    private SoramaroFirstToDream(final SoramaroFirstToDream card) {
        super(card);
    }

    @Override
    public SoramaroFirstToDream copy() {
        return new SoramaroFirstToDream(this);
    }
}
