package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class AvenHeartstabber extends CardImpl {

    public AvenHeartstabber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as there are five or more mana values among cards in your graveyard, Aven Heartstabber gets +2/+2 and has deathtouch.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                DifferentManaValuesInGraveCondition.FIVE, "as long as there are five " +
                "or more mana values among cards in your graveyard, {this} gets +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                DifferentManaValuesInGraveCondition.FIVE, "and has deathtouch"
        ));
        this.addAbility(ability.addHint(DifferentManaValuesInGraveHint.instance));

        // When Aven Heartstabber dies, mill two cards, then draw a card.
        ability = new DiesSourceTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private AvenHeartstabber(final AvenHeartstabber card) {
        super(card);
    }

    @Override
    public AvenHeartstabber copy() {
        return new AvenHeartstabber(this);
    }
}
