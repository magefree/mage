package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ProtectionFromEachOpponentAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FigureOfFable extends CardImpl {

    public FigureOfFable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/W}");

        this.subtype.add(SubType.KITHKIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G/W}: This creature becomes a Kithkin Scout with base power and toughness 2/3.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.KITHKIN, SubType.SCOUT
        ).setText("{this} becomes a Kithkin Scout"), new ManaCostsImpl<>("{G/W}"));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                2, 3, Duration.Custom
        ).setText("with base power and toughness 2/3"));
        this.addAbility(ability);

        // {1}{G/W}{G/W}: If this creature is a Scout, it becomes a Kithkin Soldier with base power and toughness 4/5.
        this.addAbility(new SimpleActivatedAbility(new FigureOfFableScoutEffect(), new ManaCostsImpl<>("{1}{G/W}{G/W}")));

        // {3}{G/W}{G/W}{G/W}: If this creature is a Soldier, it becomes a Kithkin Avatar with base power and toughness 7/8 and protection from each of your opponents.
        this.addAbility(new SimpleActivatedAbility(new FigureOfFableSoldierEffect(), new ManaCostsImpl<>("{3}{G/W}{G/W}{G/W}")));
    }

    private FigureOfFable(final FigureOfFable card) {
        super(card);
    }

    @Override
    public FigureOfFable copy() {
        return new FigureOfFable(this);
    }
}

class FigureOfFableScoutEffect extends OneShotEffect {

    FigureOfFableScoutEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Scout, it becomes a Kithkin Soldier with base power and toughness 4/5";
    }

    private FigureOfFableScoutEffect(final FigureOfFableScoutEffect effect) {
        super(effect);
    }

    @Override
    public FigureOfFableScoutEffect copy() {
        return new FigureOfFableScoutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.SCOUT, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.KITHKIN, SubType.SOLDIER
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                4, 5, Duration.Custom
        ), source);
        return true;
    }
}

class FigureOfFableSoldierEffect extends OneShotEffect {

    FigureOfFableSoldierEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Soldier, it becomes a Kithkin Avatar " +
                "with base power and toughness 7/8 and protection from each of your opponents";
    }

    private FigureOfFableSoldierEffect(final FigureOfFableSoldierEffect effect) {
        super(effect);
    }

    @Override
    public FigureOfFableSoldierEffect copy() {
        return new FigureOfFableSoldierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.SOLDIER, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.KITHKIN, SubType.AVATAR
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                7, 8, Duration.Custom
        ), source);
        game.addEffect(new GainAbilitySourceEffect(
                new ProtectionFromEachOpponentAbility(), Duration.Custom
        ), source);
        return true;
    }
}
