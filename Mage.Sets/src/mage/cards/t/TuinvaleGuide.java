package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class TuinvaleGuide extends CardImpl {

    public TuinvaleGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Celebration -- Tuinvale Guide gets +1/+0 and has lifelink as long as two or more nonland permanents entered the battlefield under your control this turn.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                CelebrationCondition.instance, "{this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield),
                CelebrationCondition.instance, "and has lifelink as long as two or more nonland "
                + "permanents entered the battlefield under your control this turn"
        ));
        ability.addHint(CelebrationCondition.getHint()).setAbilityWord(AbilityWord.CELEBRATION);
        this.addAbility(ability, new CelebrationWatcher());
    }

    private TuinvaleGuide(final TuinvaleGuide card) {
        super(card);
    }

    @Override
    public TuinvaleGuide copy() {
        return new TuinvaleGuide(this);
    }
}
