package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkawalliTheSeethingTower extends CardImpl {

    public AkawalliTheSeethingTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Descend 4 -- As long as there are four or more permanent cards in your graveyard, Akawalli, the Seething Tower gets +2/+2 and has trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), DescendCondition.FOUR,
                "as long as there are four or more permanent cards in your graveyard, {this} gets +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                DescendCondition.FOUR, "and has trample"
        ));
        this.addAbility(ability.addHint(DescendCondition.getHint()).setAbilityWord(AbilityWord.DESCEND_4));

        // Descend 8 -- As long as there are eight or more permanent cards in your graveyard, Akawalli gets an additional +2/+2 and can't be blocked by more than one creature.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), DescendCondition.EIGHT,
                "as long as there are eight or more permanent cards in your graveyard, {this} gets an additional +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new CantBeBlockedByMoreThanOneSourceEffect(), DescendCondition.EIGHT,
                "and can't be blocked by more than one creature"
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.DESCEND_8));
    }

    private AkawalliTheSeethingTower(final AkawalliTheSeethingTower card) {
        super(card);
    }

    @Override
    public AkawalliTheSeethingTower copy() {
        return new AkawalliTheSeethingTower(this);
    }
}
