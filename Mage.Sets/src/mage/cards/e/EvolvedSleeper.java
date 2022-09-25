package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvolvedSleeper extends CardImpl {

    public EvolvedSleeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}: Evolved Sleeper becomes a Human Cleric with base power and toughness 2/2.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.HUMAN, SubType.CLERIC
        ).setText("{this} becomes a Human Cleric"), new ManaCostsImpl<>("{B}"));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                2, 2, Duration.Custom, SubLayer.SetPT_7b, true
        ).setText("with base power and toughness 2/2"));
        this.addAbility(ability);

        // {1}{B}: If Evolved Sleeper is a Cleric, put a deathtouch counter on it and it becomes a Phyrexian Human Cleric with base power and toughness 3/3.
        this.addAbility(new SimpleActivatedAbility(
                new EvolvedSleeperClericEffect(), new ManaCostsImpl<>("{1}{B}")
        ));

        // {1}{B}{B}: If Evolved Sleeper is a Phyrexian, put a +1/+1 counter on it, then you draw a card and you lose 1 life.
        this.addAbility(new SimpleActivatedAbility(
                new EvolvedSleeperPhyrexianEffect(), new ManaCostsImpl<>("{1}{B}{B}")
        ));
    }

    private EvolvedSleeper(final EvolvedSleeper card) {
        super(card);
    }

    @Override
    public EvolvedSleeper copy() {
        return new EvolvedSleeper(this);
    }
}

class EvolvedSleeperClericEffect extends OneShotEffect {

    EvolvedSleeperClericEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Cleric, put a deathtouch counter on it " +
                "and it becomes a Phyrexian Human Cleric with base power and toughness 3/3";
    }

    private EvolvedSleeperClericEffect(final EvolvedSleeperClericEffect effect) {
        super(effect);
    }

    @Override
    public EvolvedSleeperClericEffect copy() {
        return new EvolvedSleeperClericEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.CLERIC, game)) {
            return false;
        }
        permanent.addCounters(CounterType.DEATHTOUCH.createInstance(), source.getControllerId(), source, game);
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.PHYREXIAN, SubType.HUMAN, SubType.CLERIC
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                3, 3, Duration.Custom, SubLayer.SetPT_7b, true
        ), source);
        return true;
    }
}

class EvolvedSleeperPhyrexianEffect extends OneShotEffect {

    EvolvedSleeperPhyrexianEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Phyrexian, put a +1/+1 counter on it, then you draw a card and you lose 1 life";
    }

    private EvolvedSleeperPhyrexianEffect(final EvolvedSleeperPhyrexianEffect effect) {
        super(effect);
    }

    @Override
    public EvolvedSleeperPhyrexianEffect copy() {
        return new EvolvedSleeperPhyrexianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null || !permanent.hasSubtype(SubType.PHYREXIAN, game)) {
            return false;
        }
        if (source.getSourcePermanentIfItStillExists(game) != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        Player player = game.getPlayer(source.getControllerId());
        game.applyEffects();
        player.drawCards(1, source, game);
        player.loseLife(1, game, source, false);
        return true;
    }
}
