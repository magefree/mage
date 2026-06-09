package mage.cards.b;

import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeanstalkWurm extends AdventureCard {

    public BeanstalkWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PLANT, SubType.WURM}, "{4}{G}",
                "Plant Beans",
                new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Beanstalk Wurm
        this.getLeftHalfCard().setPT(5, 4);

        // Reach
        this.getLeftHalfCard().addAbility(ReachAbility.getInstance());

        // Plant Beans
        // You may play an additional land this turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));

        finalizeCard();
    }

    private BeanstalkWurm(final BeanstalkWurm card) {
        super(card);
    }

    @Override
    public BeanstalkWurm copy() {
        return new BeanstalkWurm(this);
    }
}
