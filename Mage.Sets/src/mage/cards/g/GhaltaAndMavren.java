package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.DinosaurXXToken;
import mage.game.permanent.token.IxalanVampireToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhaltaAndMavren extends CardImpl {

    static final FilterPermanent filter = new FilterAttackingCreature("other attacking creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public GhaltaAndMavren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you attack, choose one --
        // * Create a tapped and attacking X/X green Dinosaur creature token with trample, where X is the greatest power among other attacking creatures.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new GhaltaAndMavrenEffect(), 1);

        // * Create X 1/1 white Vampire creature tokens with lifelink, where X is the number of other attacking creatures.
        ability.addMode(new Mode(new CreateTokenEffect(new IxalanVampireToken(), xValue)));
        this.addAbility(ability);
    }

    private GhaltaAndMavren(final GhaltaAndMavren card) {
        super(card);
    }

    @Override
    public GhaltaAndMavren copy() {
        return new GhaltaAndMavren(this);
    }
}

class GhaltaAndMavrenEffect extends OneShotEffect {

    GhaltaAndMavrenEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking X/X green Dinosaur creature token with trample, " +
                "where X is the greatest power among other attacking creatures";
    }

    private GhaltaAndMavrenEffect(final GhaltaAndMavrenEffect effect) {
        super(effect);
    }

    @Override
    public GhaltaAndMavrenEffect copy() {
        return new GhaltaAndMavrenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = game
                .getBattlefield()
                .getActivePermanents(GhaltaAndMavren.filter, source.getControllerId(), source, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        return new DinosaurXXToken(power).putOntoBattlefield(
                1, game, source, source.getControllerId(), true, true
        );
    }
}
