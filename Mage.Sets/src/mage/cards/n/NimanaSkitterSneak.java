package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimanaSkitterSneak extends CardImpl {

    public NimanaSkitterSneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As long as an opponent has eight or more cards in their graveyard, Nimana Skitter-Sneak gets +1/+0 and has menace.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                CardsInOpponentGraveyardCondition.EIGHT, "As long as an opponent has eight" +
                " or more cards in their graveyard, {this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility()),
                CardsInOpponentGraveyardCondition.EIGHT, "and has menace. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"
        ));
        this.addAbility(ability.addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private NimanaSkitterSneak(final NimanaSkitterSneak card) {
        super(card);
    }

    @Override
    public NimanaSkitterSneak copy() {
        return new NimanaSkitterSneak(this);
    }
}
