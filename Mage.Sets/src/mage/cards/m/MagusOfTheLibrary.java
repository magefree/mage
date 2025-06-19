package mage.cards.m;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MagusOfTheLibrary extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 7);

    public MagusOfTheLibrary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Draw a card. Activate this ability only if you have exactly seven cards in hand.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1), new TapSourceCost(), condition
        ));
    }

    private MagusOfTheLibrary(final MagusOfTheLibrary card) {
        super(card);
    }

    @Override
    public MagusOfTheLibrary copy() {
        return new MagusOfTheLibrary(this);
    }
}
