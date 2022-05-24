package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KenrithTheReturnedKing extends CardImpl {

    public KenrithTheReturnedKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {R}: All creatures gain trample and haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("all creatures gain trample"), new ManaCostsImpl<>("{R}"));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and haste until end of turn"));
        this.addAbility(ability);

        // {1}{G}: Put a +1/+1 counter on target creature.
        ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{W}: Target player gains 5 life.
        ability = new SimpleActivatedAbility(new GainLifeTargetEffect(5), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {3}{U}: Target player draws a card.
        ability = new SimpleActivatedAbility(new DrawCardTargetEffect(1), new ManaCostsImpl<>("{3}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {4}{B}: Put target creature card from a graveyard onto the battlefield under its owner's control.
        ability = new SimpleActivatedAbility(new KenrithTheReturnedKingEffect(), new ManaCostsImpl<>("{4}{B}"));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private KenrithTheReturnedKing(final KenrithTheReturnedKing card) {
        super(card);
    }

    @Override
    public KenrithTheReturnedKing copy() {
        return new KenrithTheReturnedKing(this);
    }
}

class KenrithTheReturnedKingEffect extends OneShotEffect {

    KenrithTheReturnedKingEffect() {
        super(Outcome.Benefit);
        staticText = "put target creature card from a graveyard onto the battlefield under its owner's control";
    }

    private KenrithTheReturnedKingEffect(final KenrithTheReturnedKingEffect effect) {
        super(effect);
    }

    @Override
    public KenrithTheReturnedKingEffect copy() {
        return new KenrithTheReturnedKingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        Player player = game.getPlayer(card.getOwnerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}