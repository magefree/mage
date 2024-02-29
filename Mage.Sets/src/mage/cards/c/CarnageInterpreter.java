package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CarnageInterpreter extends CardImpl {

    public CarnageInterpreter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/R}{B/R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Carnage Interpreter enters the battlefield, discard your hand, then investigate four times.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect());
        ability.addEffect(new InvestigateEffect(4).concatBy(", then"));
        this.addAbility(ability);

        // As long as you have one or fewer cards in hand, Carnage Interpreter gets +2/+2 and has menace.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), HeckbentCondition.instance,
                "as long as you have one or fewer cards in hand, {this} gets +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility(false)),
                HeckbentCondition.instance, "and has menace"
        ));
        this.addAbility(ability);
    }

    private CarnageInterpreter(final CarnageInterpreter card) {
        super(card);
    }

    @Override
    public CarnageInterpreter copy() {
        return new CarnageInterpreter(this);
    }
}
