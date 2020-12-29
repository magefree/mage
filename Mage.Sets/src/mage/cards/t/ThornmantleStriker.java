package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ThornmantleStriker extends CardImpl {

    private static final PermanentsOnBattlefieldCount elfCount
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ELF));
    private static final SignInversionDynamicValue negativeElfCount
            = new SignInversionDynamicValue(elfCount);

    public ThornmantleStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Thornmantle Striker enters the battlefield, choose one —
        // • Remove X counters from target permanent, where X is the number of Elves you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ThornmantleStrikerEffect(elfCount));
        ability.addTarget(new TargetPermanent());

        // • Target creature an opponent controls gets -X/-X until end of turn, where X is the number of Elves you control.
        Mode mode = new Mode(new BoostTargetEffect(negativeElfCount, negativeElfCount, Duration.EndOfTurn, true
        ).setText("Target creature an opponent controls gets -X/-X until end of turn, where X is the number of Elves you control"));
        mode.addTarget(new TargetOpponentsCreaturePermanent());

        ability.addMode(mode);
        this.addAbility(ability);
    }

    private ThornmantleStriker(final ThornmantleStriker card) {
        super(card);
    }

    @Override
    public ThornmantleStriker copy() {
        return new ThornmantleStriker(this);
    }
}

class ThornmantleStrikerEffect extends OneShotEffect {

    private final DynamicValue xValue;

    ThornmantleStrikerEffect(DynamicValue xValue) {
        super(Outcome.AIDontUseIt);
        staticText = "Remove X counters from target permanent, where X is the number of Elves you control";
        this.xValue = xValue;
    }

    private ThornmantleStrikerEffect(final ThornmantleStrikerEffect effect) {
        super(effect);
        this.xValue = effect.xValue.copy();
    }

    @Override
    public ThornmantleStrikerEffect copy() {
        return new ThornmantleStrikerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && permanent != null) {
            int toRemove = xValue.calculate(game, source, this);
            int removed = 0;
            String[] counterNames = permanent.getCounters(game).keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (controller.chooseUse(Outcome.Neutral, "Do you want to remove " + counterName + " counters?", source, game)) {
                    if (permanent.getCounters(game).get(counterName).getCount() == 1 || (toRemove - removed == 1)) {
                        permanent.removeCounters(counterName, 1, source, game);
                        removed++;
                    } else {
                        int amount = controller.getAmount(1, Math.min(permanent.getCounters(game).get(counterName).getCount(), toRemove - removed), "How many?", game);
                        if (amount > 0) {
                            removed += amount;
                            permanent.removeCounters(counterName, amount, source, game);
                        }
                    }
                }
                if (removed >= toRemove) {
                    break;
                }
            }
            return true;
        }
        return false;
    }
}
