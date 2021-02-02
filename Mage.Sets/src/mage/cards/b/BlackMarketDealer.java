
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class BlackMarketDealer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Rogue or Hunter creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(SubType.ROGUE.getPredicate(), SubType.HUNTER.getPredicate()));
    }

    public BlackMarketDealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Rogue or Hunter creature you control dies, put a bounty counter on target creature an opponent controls.
        Ability ability = new DiesCreatureTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), false, filter);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, target player loses 1 life and draws a card.
        ability = new BountyAbility(new LoseLifeTargetEffect(1));
        Effect effect = new DrawCardTargetEffect(1);
        effect.setText("and draws a card");
        ability.addEffect(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private BlackMarketDealer(final BlackMarketDealer card) {
        super(card);
    }

    @Override
    public BlackMarketDealer copy() {
        return new BlackMarketDealer(this);
    }
}
