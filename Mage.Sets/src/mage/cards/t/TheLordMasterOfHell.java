package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLordMasterOfHell extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland cards in your graveyard");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Hint hint = new ValueHint("Noncreature, nonland cards in your graveyard", xValue);

    public TheLordMasterOfHell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.nightCard = true;
        this.color.setBlue(true);
        this.color.setRed(true);

        // Starfall -- Whenever The Lord Master of Hell attacks, it deals X damage to each opponent, where X is the number of noncreature, nonland cards in your graveyard.
        this.addAbility(new AttacksTriggeredAbility(new DamagePlayersEffect(
                xValue, TargetController.OPPONENT
        ).setText("it deals X damage to each opponent, where X is " +
                "the number of noncreature, nonland cards in your graveyard"))
                .withFlavorWord("Starfall").addHint(hint));
    }

    private TheLordMasterOfHell(final TheLordMasterOfHell card) {
        super(card);
    }

    @Override
    public TheLordMasterOfHell copy() {
        return new TheLordMasterOfHell(this);
    }
}
