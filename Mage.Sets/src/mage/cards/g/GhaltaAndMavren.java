package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.*;

import java.util.UUID;

public class GhaltaAndMavren extends CardImpl {
    public GhaltaAndMavren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.DINOSAUR);
        this.addSubType(SubType.VAMPIRE);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        //Whenever you attack, choose one —
        //• Create a tapped and attacking X/X green Dinosaur creature token with trample, where X is the greatest power
        //  among other attacking creatures.
        //• Create X 1/1 white Vampire creature tokens with lifelink, where X is the number of other attacking creatures.
        AttacksWithCreaturesTriggeredAbility attacksWithCreaturesTriggeredAbility =
                new AttacksWithCreaturesTriggeredAbility(new GhaltaAndMavrenDinosaurEffect(), 1);
        attacksWithCreaturesTriggeredAbility.addMode(new Mode(new GhaltaAndMavrenVampireEffect()));
        this.addAbility(attacksWithCreaturesTriggeredAbility);
    }

    private GhaltaAndMavren(final GhaltaAndMavren card) {
        super(card);
    }

    @Override
    public GhaltaAndMavren copy() {
        return new GhaltaAndMavren(this);
    }
}

class GhaltaAndMavrenDinosaurEffect extends OneShotEffect {

    GhaltaAndMavrenDinosaurEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking X/X green Dinosaur creature token with trample, where X is the " +
                "greatest power among other attacking creatures";
    }

    private GhaltaAndMavrenDinosaurEffect(final GhaltaAndMavrenDinosaurEffect effect) {
        super(effect);
    }

    @Override
    public GhaltaAndMavrenDinosaurEffect copy() {
        return new GhaltaAndMavrenDinosaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)
                .stream()
                .filter(Permanent::isAttacking)
                .filter(p -> p.getId() != source.getSourceId())
                .mapToInt(p -> p.getPower().getValue())
                .max().orElse(0);
        return new DinosaurBeastToken(power).putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
    }
}

class GhaltaAndMavrenVampireEffect extends OneShotEffect {

    GhaltaAndMavrenVampireEffect() {
        super(Outcome.Benefit);
        staticText = "create X 1/1 white Vampire creature tokens with lifelink, where X is the number of other attacking creatures";
    }

    private GhaltaAndMavrenVampireEffect(final GhaltaAndMavrenVampireEffect effect) {
        super(effect);
    }

    @Override
    public GhaltaAndMavrenVampireEffect copy() {
        return new GhaltaAndMavrenVampireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (int) game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)
                .stream()
                .filter(Permanent::isAttacking)
                .filter(p -> p.getId() != source.getSourceId())
                .count();
        return new VampireWhiteLifelinkToken().putOntoBattlefield(amount, game, source, source.getControllerId());
    }
}
