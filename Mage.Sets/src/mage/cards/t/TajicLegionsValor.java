package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Collections;
import java.util.UUID;

public final class TajicLegionsValor extends CardImpl {

    public TajicLegionsValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(IndestructibleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new TajicLegionsValorEffect()));
    }

    private TajicLegionsValor(final TajicLegionsValor card) {
        super(card);
    }

    @Override
    public TajicLegionsValor copy() {
        return new TajicLegionsValor(this);
    }
}

class TajicLegionsValorEffect extends OneShotEffect {

    TajicLegionsValorEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on {this}. Then conjure a card with mana value equal to the number of +1/+1 counters on it from {this}'s spellbook onto the battlefield. It gains haste until end of turn and attacks this combat if able";
    }

    private TajicLegionsValorEffect(final TajicLegionsValorEffect effect) {
        super(effect);
    }

    @Override
    public TajicLegionsValorEffect copy() {
        return new TajicLegionsValorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        Permanent current = game.getPermanent(source.getSourceId());
        if (current != null) {
            current.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        int counters = permanent.getCounters(game).getCount(CounterType.P1P1);
        String[] spellbook = {"Boros Recruit", "Swiftblade Vindicator", "Skyknight Legionnaire", "Spark Trooper", "Aurelia, the Law Above"};
        if (counters < 1 || counters > spellbook.length) {
            return true;
        }
        CardInfo info = CardRepository.instance.findCards(new CardCriteria().name(spellbook[counters - 1])).stream().findFirst().orElse(null);
        if (info == null) {
            return false;
        }
        Card card = info.createCard();
        game.loadCards(Collections.singleton(card), player.getId());
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(card, game)), source);
        game.addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}
