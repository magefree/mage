package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AscendantSpirit extends CardImpl {

    public AscendantSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {S}{S}: Ascendant Spirit becomes a Spirit Warrior with base power and toughness 2/3.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.SPIRIT, SubType.WARRIOR
        ).setText("{this} becomes a Spirit Warrior"), new ManaCostsImpl<>("{S}{S}"));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                2,
                3,
                Duration.WhileOnBattlefield,
                SubLayer.SetPT_7b,
                true
        ).setText("with base power and toughness 2/3"));
        this.addAbility(ability);

        // {S}{S}{S}: If Ascendant Spirit is a Warrior, put a flying counter on it and it becomes a Spirit Warrior Angel with base power and toughness 4/4.
        this.addAbility(new SimpleActivatedAbility(
                new AscendantSpiritWarriorEffect(), new ManaCostsImpl<>("{S}{S}{S}")
        ));

        // {S}{S}{S}{S}: If Ascendant Spirit is an Angel, put two +1/+1 counters on it and it gains "Whenever this creature deals damage to a player, draw a card."
        this.addAbility(new SimpleActivatedAbility(
                new AscendantSpiritAngelEffect(), new ManaCostsImpl<>("{S}{S}{S}{S}")
        ));
    }

    private AscendantSpirit(final AscendantSpirit card) {
        super(card);
    }

    @Override
    public AscendantSpirit copy() {
        return new AscendantSpirit(this);
    }
}

class AscendantSpiritWarriorEffect extends OneShotEffect {

    AscendantSpiritWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Warrior, put a flying counter on it " +
                "and it becomes a Spirit Warrior Angel with base power and toughness 4/4";
    }

    private AscendantSpiritWarriorEffect(final AscendantSpiritWarriorEffect effect) {
        super(effect);
    }

    @Override
    public AscendantSpiritWarriorEffect copy() {
        return new AscendantSpiritWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.WARRIOR, game)) {
            return false;
        }
        permanent.addCounters(CounterType.FLYING.createInstance(), source.getControllerId(), source, game);
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.SPIRIT, SubType.WARRIOR, SubType.ANGEL
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                4, 4, Duration.Custom, SubLayer.SetPT_7b, true
        ), source);
        return true;
    }
}

class AscendantSpiritAngelEffect extends OneShotEffect {

    AscendantSpiritAngelEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is an Angel, put two +1/+1 counters on it and it gains " +
                "\"Whenever this creature deals combat damage to a player, draw a card.\"";
    }

    private AscendantSpiritAngelEffect(final AscendantSpiritAngelEffect effect) {
        super(effect);
    }

    @Override
    public AscendantSpiritAngelEffect copy() {
        return new AscendantSpiritAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.ANGEL, game)) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
        game.addEffect(new GainAbilitySourceEffect(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ), Duration.Custom), source);
        return true;
    }
}
