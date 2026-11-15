package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class VoidExtrapolator extends PrepareCard {

    public VoidExtrapolator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}", "Omit Variables", new CardType[]{CardType.SORCERY}, "{U/B}");

        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Threshold -- This creature gets +1/+1 as long as there are seven or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
            "this creature gets +1/+1 as long as there are seven or more cards in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));

        // Omit Variables
        // Sorcery {U/B}
        // Mill three cards.
        this.getSpellCard().getSpellAbility().addEffect(new MillCardsControllerEffect(3));
    }

    private VoidExtrapolator(final VoidExtrapolator card) {
        super(card);
    }

    @Override
    public VoidExtrapolator copy() {
        return new VoidExtrapolator(this);
    }
}
