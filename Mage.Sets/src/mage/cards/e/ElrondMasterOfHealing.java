package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetOpponentAllTriggeredAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElrondMasterOfHealing extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public ElrondMasterOfHealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you scry, put a +1/+1 counter on each of up to X target creatures, where X is the number of cards looked at while scrying this way.
        this.addAbility(new ScryTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to X target creatures, " +
                        "where X is the number of cards looked at while scrying this way"))
                .setTargetAdjuster(ElrondMasterOfHealingAdjuster.instance));

        // Whenever a creature you control with a +1/+1 counter on it becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetOpponentAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, true
        ));
    }

    private ElrondMasterOfHealing(final ElrondMasterOfHealing card) {
        super(card);
    }

    @Override
    public ElrondMasterOfHealing copy() {
        return new ElrondMasterOfHealing(this);
    }
}

enum ElrondMasterOfHealingAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int amount = ability
                .getEffects()
                .stream()
                .mapToInt(effect -> (Integer) effect.getValue("amount"))
                .findFirst()
                .orElse(0);
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(0, amount));
    }
}
