
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public final class ChampionOfLambholt extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ChampionOfLambholt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creatures with power less than Champion of Lambholt's power can't block creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChampionOfLambholtEffect()));

        // Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Champion of Lambholt.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false, null, true));

    }

    public ChampionOfLambholt(final ChampionOfLambholt card) {
        super(card);
    }

    @Override
    public ChampionOfLambholt copy() {
        return new ChampionOfLambholt(this);
    }
}

class ChampionOfLambholtEffect extends RestrictionEffect {

    ChampionOfLambholtEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with power less than {this}'s power can't block creatures you control";
    }

    ChampionOfLambholtEffect(final ChampionOfLambholtEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && attacker.isControlledBy(sourcePermanent.getControllerId())) {
            return blocker.getPower().getValue() >= sourcePermanent.getPower().getValue();
        }
        return true;
    }

    @Override
    public ChampionOfLambholtEffect copy() {
        return new ChampionOfLambholtEffect(this);
    }
}
