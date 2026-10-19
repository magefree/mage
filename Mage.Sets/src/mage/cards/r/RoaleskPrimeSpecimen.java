package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Collections;
import java.util.UUID;

public final class RoaleskPrimeSpecimen extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("{this} or another permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RoaleskPrimeSpecimen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Roalesk, Prime Specimen or another permanent you control is turned face up, you may pay {X}. If you do, if X is 1 or more, conjure a duplicate of a random creature card with mana value X into your hand. Cloak it. It gains "{G/U}: Turn this creature face up" for as long as it remains face down.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(new RoaleskPrimeSpecimenEffect(), filter));
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{G}{U}")));
    }

    private RoaleskPrimeSpecimen(final RoaleskPrimeSpecimen card) {
        super(card);
    }

    @Override
    public RoaleskPrimeSpecimen copy() {
        return new RoaleskPrimeSpecimen(this);
    }
}

class RoaleskPrimeSpecimenEffect extends OneShotEffect {

    RoaleskPrimeSpecimenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may pay {X}. If you do, if X is 1 or more, conjure a duplicate of a random creature card "
                + "with mana value X into your hand. Cloak it. It gains \"{G/U}: Turn this creature face up\" "
                + "for as long as it remains face down";
    }

    private RoaleskPrimeSpecimenEffect(final RoaleskPrimeSpecimenEffect effect) {
        super(effect);
    }

    @Override
    public RoaleskPrimeSpecimenEffect copy() {
        return new RoaleskPrimeSpecimenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(outcome, "Pay {X}?", source, game)) {
            return false;
        }
        int x = controller.announceX(0, Integer.MAX_VALUE, "Choose the value of X", game, source, true);
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        cost.add(new GenericManaCost(x));
        if (!cost.pay(source, game, source, controller.getId(), false, null) || x < 1) {
            return false;
        }
        Card card = ConjureRandomCreatureEffect.conjure(x, Zone.HAND, game, source);
        if (card == null || !controller.getHand().contains(card.getId())) {
            return false;
        }
        for (Permanent permanent : ManifestEffect.doManifestCards(game, source, controller, Collections.singleton(card), true)) {
            game.addEffect(new RoaleskTurnFaceUpEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}

class RoaleskTurnFaceUpEffect extends GainAbilityTargetEffect {

    RoaleskTurnFaceUpEffect() {
        super(new TurnFaceUpAbility(makeCosts()), Duration.Custom);
    }

    private static Costs<Cost> makeCosts() {
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new ManaCostsImpl<>("{G/U}"));
        return costs;
    }

    private RoaleskTurnFaceUpEffect(final RoaleskTurnFaceUpEffect effect) {
        super(effect);
    }

    @Override
    public RoaleskTurnFaceUpEffect copy() {
        return new RoaleskTurnFaceUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.isFaceDown(game)) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
