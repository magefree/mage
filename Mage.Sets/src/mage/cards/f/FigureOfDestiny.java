package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FigureOfDestiny extends CardImpl {

    public FigureOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/W}");
        this.subtype.add(SubType.KITHKIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {RW}: Figure of Destiny becomes a Kithkin Spirit with base power and toughness 2/2.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.KITHKIN, SubType.SPIRIT
        ).setText("{this} becomes a Kithkin Spirit"), new ManaCostsImpl<>("{R/W}"));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                2, 2, Duration.Custom, SubLayer.SetPT_7b, true
        ).setText("with base power and toughness 2/2"));
        this.addAbility(ability);

        // {RW}{RW}{RW}: If Figure of Destiny is a Spirit, it becomes a Kithkin Spirit Warrior with base power and toughness 4/4.
        this.addAbility(new SimpleActivatedAbility(
                new FigureOfDestinySpiritEffect(), new ManaCostsImpl<>("{R/W}{R/W}{R/W}")
        ));

        // {RW}{RW}{RW}{RW}{RW}{RW}: If Figure of Destiny is a Warrior, it becomes a Kithkin Spirit Warrior Avatar with base power and toughness 8/8, flying, and first strike.
        this.addAbility(new SimpleActivatedAbility(
                new FigureOfDestinyWarriorEffect(), new ManaCostsImpl<>("{R/W}{R/W}{R/W}{R/W}{R/W}{R/W}")
        ));
    }

    private FigureOfDestiny(final FigureOfDestiny card) {
        super(card);
    }

    @Override
    public FigureOfDestiny copy() {
        return new FigureOfDestiny(this);
    }
}

class FigureOfDestinySpiritEffect extends OneShotEffect {

    FigureOfDestinySpiritEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Spirit, it becomes a Kithkin Spirit Warrior with base power and toughness 4/4";
    }

    private FigureOfDestinySpiritEffect(final FigureOfDestinySpiritEffect effect) {
        super(effect);
    }

    @Override
    public FigureOfDestinySpiritEffect copy() {
        return new FigureOfDestinySpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.SPIRIT, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.KITHKIN, SubType.SPIRIT, SubType.WARRIOR
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                4, 4, Duration.Custom, SubLayer.SetPT_7b, true
        ), source);
        return true;
    }
}

class FigureOfDestinyWarriorEffect extends OneShotEffect {

    FigureOfDestinyWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Warrior, it becomes a Kithkin Spirit Warrior Avatar " +
                "with base power and toughness 8/8, flying, and first strike";
    }

    private FigureOfDestinyWarriorEffect(final FigureOfDestinyWarriorEffect effect) {
        super(effect);
    }

    @Override
    public FigureOfDestinyWarriorEffect copy() {
        return new FigureOfDestinyWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.WARRIOR, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.KITHKIN, SubType.SPIRIT, SubType.WARRIOR, SubType.AVATAR
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                8, 8, Duration.Custom, SubLayer.SetPT_7b, true
        ), source);
        game.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.Custom
        ), source);
        game.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.Custom
        ), source);
        return true;
    }
}
