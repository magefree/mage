package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Flutterfox extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public Flutterfox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.FOX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you control an artifact or enchantment, Flutterfox has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                condition, "As long as you control an artifact or enchantment, {this} has flying."
        )));
    }

    private Flutterfox(final Flutterfox card) {
        super(card);
    }

    @Override
    public Flutterfox copy() {
        return new Flutterfox(this);
    }
}
