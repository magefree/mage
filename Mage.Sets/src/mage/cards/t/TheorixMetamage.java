package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheorixMetamage extends PrepareCard {

    public TheorixMetamage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}", "Omit Variables", new CardType[]{CardType.SORCERY}, "{U/B}");

        this.subtype.add(SubType.SHADE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Threshold -- This creature gets +1/+0 and has flying as long as there are seven or more cards in your graveyard.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield), ThresholdCondition.instance,
            "This creature gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(FlyingAbility.getInstance()),
            ThresholdCondition.instance, "and has flying as long as there are seven or more cards in your graveyard"
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);

        // Omit Variables
        // Sorcery {U/B}
        // Mill three cards.
        this.getSpellCard().getSpellAbility().addEffect(new MillCardsControllerEffect(3));
    }

    private TheorixMetamage(final TheorixMetamage card) {
        super(card);
    }

    @Override
    public TheorixMetamage copy() {
        return new TheorixMetamage(this);
    }
}
