package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GrandBallGuest extends CardImpl {

    public GrandBallGuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Celebration -- Grand Ball Guest gets +1/+1 and has trample as long as two or more nonland permanents entered the battlefield under your control this turn.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                CelebrationCondition.instance, "{this} gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                CelebrationCondition.instance, "and has trample as long as two or more nonland "
                + "permanents entered the battlefield under your control this turn"
        ));
        ability.addHint(CelebrationCondition.getHint()).setAbilityWord(AbilityWord.CELEBRATION);
        this.addAbility(ability, new CelebrationWatcher());
    }

    private GrandBallGuest(final GrandBallGuest card) {
        super(card);
    }

    @Override
    public GrandBallGuest copy() {
        return new GrandBallGuest(this);
    }
}
