package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.token.DinosaurBeastToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuartzwoodCrasher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control with trample");

    static {
        filter.add(new AbilityPredicate(TrampleAbility.class));
    }

    public QuartzwoodCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more creatures you control with trample deal combat damage to a player, create an X/X green Dinosaur Beast creature token with trample, where X is the amount of damage those creatures dealt to that player.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new QuartzwoodCrasherEffect(), filter));
    }

    private QuartzwoodCrasher(final QuartzwoodCrasher card) {
        super(card);
    }

    @Override
    public QuartzwoodCrasher copy() {
        return new QuartzwoodCrasher(this);
    }
}

class QuartzwoodCrasherEffect extends OneShotEffect {

    QuartzwoodCrasherEffect() {
        super(Outcome.Benefit);
        this.staticText = "create an X/X green Dinosaur Beast creature token with trample, " +
                "where X is the amount of damage those creatures dealt to that player.";
    }

    private QuartzwoodCrasherEffect(final QuartzwoodCrasherEffect effect) {
        super(effect);
    }

    @Override
    public QuartzwoodCrasherEffect copy() {
        return new QuartzwoodCrasherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new DinosaurBeastToken(SavedDamageValue.MUCH.calculate(game, source, this)
        ).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
