package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class SauronDinoDevotee extends CardImpl {

    public SauronDinoDevotee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sauron enters or attacks, choose one --
        // * Cure Cancer -- You gain 3 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GainLifeEffect(3))
                .withFirstModeFlavorWord("Cure Cancer");

        // * Turn People into Dinosaurs -- Put a saurian counter on another target creature. It's a green Dinosaur with base power and toughness 5/5 for as long as it has a saurian counter on it.
        ability.addMode(new Mode(new AddCountersTargetEffect(CounterType.SAURIAN.createInstance()))
                .addEffect(new SauronDinoDevoteeEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE))
                .withFlavorWord("Turn People into Dinosaurs"));
        this.addAbility(ability);
    }

    private SauronDinoDevotee(final SauronDinoDevotee card) {
        super(card);
    }

    @Override
    public SauronDinoDevotee copy() {
        return new SauronDinoDevotee(this);
    }
}

class SauronDinoDevoteeEffect extends BecomesCreatureTargetEffect {

    SauronDinoDevoteeEffect() {
        super(new CreatureToken(5, 5, "green Dinosaur with base power and toughness 5/5")
                .withColor("G").withSubType(SubType.DINOSAUR), false, false, Duration.Custom);
        this.staticText = "It's a green Dinosaur with base power and toughness 5/5 " +
                "for as long as it has a saurian counter on it";
    }

    private SauronDinoDevoteeEffect(final SauronDinoDevoteeEffect effect) {
        super(effect);
    }

    @Override
    public SauronDinoDevoteeEffect copy() {
        return new SauronDinoDevoteeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature == null || creature.getCounters(game).getCount(CounterType.SAURIAN) < 1) {
            this.discard();
            return false;
        }
        return super.apply(layer, sublayer, source, game);
    }
}
