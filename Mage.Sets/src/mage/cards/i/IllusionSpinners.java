package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IllusionSpinners extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.FAERIE, "you control a Faerie")
    );

    public IllusionSpinners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // You may cast this spell as though it had flash if you control a Faerie.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(
                condition, "you may cast this spell as though it had flash if you control a Faerie"
        ));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature has hexproof as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(
                HexproofAbility.getInstance(),
                Duration.WhileOnBattlefield
            ), SourceTappedCondition.UNTAPPED,
            "{this} has hexproof as long as it's untapped"
        )));
    }

    private IllusionSpinners(final IllusionSpinners card) {
        super(card);
    }

    @Override
    public IllusionSpinners copy() {
        return new IllusionSpinners(this);
    }
}
