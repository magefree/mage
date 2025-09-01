package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HavocEater extends CardImpl {

    public HavocEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Havoc Eater enters the battlefield, for each opponent, goad up to one target creature that opponent controls. Put X +1/+1 counters on Havoc Eater, where X is the total power of creatures goaded this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GoadTargetEffect()
                        .setText("for each opponent, goad up to one target creature that opponent controls")
                        .setTargetPointer(new EachTargetPointer()));
        ability.addEffect(new HavocEaterEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true)));
    }

    private HavocEater(final HavocEater card) {
        super(card);
    }

    @Override
    public HavocEater copy() {
        return new HavocEater(this);
    }
}

class HavocEaterEffect extends OneShotEffect {

    HavocEaterEffect() {
        super(Outcome.Benefit);
        staticText = "put X +1/+1 counters on {this}, where X is the total power of creatures goaded this way";
        this.setTargetPointer(new EachTargetPointer());
    }

    private HavocEaterEffect(final HavocEaterEffect effect) {
        super(effect);
    }

    @Override
    public HavocEaterEffect copy() {
        return new HavocEaterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int amount = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        return amount > 0 && permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
    }
}
