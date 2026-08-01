package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class OinTheBrave extends CardImpl {

    public OinTheBrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Storied
        this.addAbility(new StoriedAbility());

        // As long as you have an enduring story, Oin gets +1/+0 and has haste.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
            EnduringStoryCondition.instance,
            "as long as you have an enduring story, {this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HasteAbility.getInstance()),
            EnduringStoryCondition.instance,
            "and has haste"
        ));
        this.addAbility(ability.addHint(EnduringStoryHint.instance));

        // {1}, {T}, Discard a card: Draw a card.
        Ability activatedAbility = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addCost(new DiscardCardCost());
        this.addAbility(activatedAbility);
    }

    private OinTheBrave(final OinTheBrave card) {
        super(card);
    }

    @Override
    public OinTheBrave copy() {
        return new OinTheBrave(this);
    }
}
