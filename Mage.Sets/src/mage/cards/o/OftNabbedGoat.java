package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OftNabbedGoat extends CardImpl {

    public OftNabbedGoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // {1}: Draw a card. Gain control of this creature and put a -1/-1 counter on it. Only your opponents may activate this ability and only as a sorcery.
        ActivateAsSorceryActivatedAbility ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(1).setText("Draw a card"), new GenericManaCost(1)
        );
        ability.addEffect(new OftNabbedGoatGainControlEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.M1M1.createInstance()).setText("and put a -1/-1 counter on it"));
        ability.setMayActivate(TargetController.OPPONENT);
        this.addAbility(ability);

        // When this creature dies, if it had one or more -1/-1 counters on it, its owner draws that many cards and each other player loses that much life.
        this.addAbility(new DiesSourceTriggeredAbility(new OftNabbedGoatOwnerDrawsEffect()).withInterveningIf(OftNabbedGoatCondition.instance));
    }

    private OftNabbedGoat(final OftNabbedGoat card) {
        super(card);
    }

    @Override
    public OftNabbedGoat copy() {
        return new OftNabbedGoat(this);
    }
}

class OftNabbedGoatGainControlEffect extends OneShotEffect {

    OftNabbedGoatGainControlEffect() {
        super(Outcome.GainControl);
        staticText = "Gain control of this creature";
    }

    private OftNabbedGoatGainControlEffect(final OftNabbedGoatGainControlEffect effect) {
        super(effect);
    }

    @Override
    public OftNabbedGoatGainControlEffect copy() {
        return new OftNabbedGoatGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player abilityController = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (abilityController == null || permanent == null) {
            return false;
        }

        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, abilityController.getId()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

enum OftNabbedGoatCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> permanent.getCounters(game).getCount(CounterType.M1M1) >= 1)
                .isPresent();
    }

    @Override
    public String toString() {
        return "if it had one or more -1/-1 counters on it";
    }
}

class OftNabbedGoatOwnerDrawsEffect extends OneShotEffect {

    OftNabbedGoatOwnerDrawsEffect() {
        super(Outcome.DrawCard);
        staticText = "its owner draws that many cards and each other player loses that much life";
    }

    private OftNabbedGoatOwnerDrawsEffect(final OftNabbedGoatOwnerDrawsEffect effect) {
        super(effect);
    }

    @Override
    public OftNabbedGoatOwnerDrawsEffect copy() {
        return new OftNabbedGoatOwnerDrawsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        Player owner = game.getPlayer(permanent.getOwnerId());
        if (owner == null) {
            return false;
        }
        int counterCount = permanent.getCounters(game).getCount(CounterType.M1M1);
        owner.drawCards(counterCount, source, game);
        for (UUID opponentId : game.getOpponents(owner.getId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                player.loseLife(counterCount, game, source, false);
            }
        }
        return true;
    }
}
