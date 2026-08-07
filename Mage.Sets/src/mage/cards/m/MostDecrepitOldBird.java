package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 * @author muz
 */
public final class MostDecrepitOldBird extends AdventureCard {

    public MostDecrepitOldBird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{U}", "Speak Secrets", "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Threshold -- This creature gets +1/+1 as long as there are seven or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "this creature gets +1/+1 as long as there are seven or more cards in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));

        // Speak Secrets
        // Mill four cards, then put an instant or sorcery card from among them into your hand.
        this.getSpellCard().getSpellAbility().addEffect(
            new MillThenPutInHandEffect(4, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, false)
        );

        this.finalizeAdventure();
    }

    private MostDecrepitOldBird(final MostDecrepitOldBird card) {
        super(card);
    }

    @Override
    public MostDecrepitOldBird copy() {
        return new MostDecrepitOldBird(this);
    }
}
