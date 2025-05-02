package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class OmnivorousFlytrap extends CardImpl {

    public OmnivorousFlytrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Delirium -- Whenever Omnivorous Flytrap enters or attacks, if there are four or more card types among cards in your graveyard, distribute two +1/+1 counters among one or two target creatures. Then if there are six or more card types among cards in your graveyard, double the number of +1/+1 counters on those creatures.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DistributeCountersEffect())
                .withInterveningIf(DeliriumCondition.instance);
        ability.addEffect(new ConditionalOneShotEffect(
                new OmnivorousFlytrapEffect(),
                new OmnivorousFlytrapCondition())
                .concatBy("Then"));
        ability.addTarget(new TargetCreaturePermanentAmount(2));
        ability.addHint(CardTypesInGraveyardCount.YOU.getHint());
        this.addAbility(ability.setAbilityWord(AbilityWord.DELIRIUM));
    }

    private OmnivorousFlytrap(final OmnivorousFlytrap card) {
        super(card);
    }

    @Override
    public OmnivorousFlytrap copy() {
        return new OmnivorousFlytrap(this);
    }
}

class OmnivorousFlytrapEffect extends OneShotEffect {

    OmnivorousFlytrapEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on those creatures";
    }

    private OmnivorousFlytrapEffect(final OmnivorousFlytrapEffect effect) {
        super(effect);
    }

    @Override
    public OmnivorousFlytrapEffect copy() {
        return new OmnivorousFlytrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                int existingCounters = permanent.getCounters(game).getCount(CounterType.P1P1);
                if (existingCounters > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(existingCounters), source, game);
                }
            }
        }
        return true;
    }
}

class OmnivorousFlytrapCondition extends IntCompareCondition {

    OmnivorousFlytrapCondition() {
        super(ComparisonType.OR_GREATER, 6);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return CardTypesInGraveyardCount.YOU.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "if there are six or more card types among cards in your graveyard";
    }
}
