package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class Frightcrawler extends CardImpl {

    public Frightcrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Frightcrawler gets +2/+2 and can't block.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                ThresholdCondition.instance, "If seven or more cards are in your graveyard, {this} gets +2/+2 "
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield),
                ThresholdCondition.instance, "and can't block."
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private Frightcrawler(final Frightcrawler card) {
        super(card);
    }

    @Override
    public Frightcrawler copy() {
        return new Frightcrawler(this);
    }
}
