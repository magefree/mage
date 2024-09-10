package mage.cards.b;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{G}", "Plant Beans", "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Plant Beans
        // You may play an additional land this turn.
        this.getSpellCard().getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        
        this.finalizeAdventure();
    }

    private BeanstalkWurm(final BeanstalkWurm card) {
        super(card);
    }

    @Override
    public BeanstalkWurm copy() {
        return new BeanstalkWurm(this);
    }
}
