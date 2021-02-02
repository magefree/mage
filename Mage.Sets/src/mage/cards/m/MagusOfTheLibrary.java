
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MagusOfTheLibrary extends CardImpl {

    public MagusOfTheLibrary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {tap}: Draw a card. Activate this ability only if you have exactly seven cards in hand.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost(),
                new CardsInHandCondition(ComparisonType.EQUAL_TO, 7),
                ""));
    }

    private MagusOfTheLibrary(final MagusOfTheLibrary card) {
        super(card);
    }

    @Override
    public MagusOfTheLibrary copy() {
        return new MagusOfTheLibrary(this);
    }
}
