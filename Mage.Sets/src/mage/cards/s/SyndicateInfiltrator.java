package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyndicateInfiltrator extends CardImpl {

    public SyndicateInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as there are five or more mana values among cards in your graveyard, Syndicate Infiltrator gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                DifferentManaValuesInGraveCondition.FIVE, "as long as there are five " +
                "or more mana values among cards in your graveyard, {this} gets +2/+2"
        )).addHint(DifferentManaValuesInGraveHint.instance));
    }

    private SyndicateInfiltrator(final SyndicateInfiltrator card) {
        super(card);
    }

    @Override
    public SyndicateInfiltrator copy() {
        return new SyndicateInfiltrator(this);
    }
}
