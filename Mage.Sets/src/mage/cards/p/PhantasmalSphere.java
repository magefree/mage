package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class PhantasmalSphere extends CardImpl {

    public PhantasmalSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put a +1/+1 counter on Phantasmal Sphere, then sacrifice Phantasmal Sphere unless you pay {1} for each +1/+1 counter on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false);
        Effect effect = new SacrificeSourceUnlessPaysEffect(new CountersSourceCount(CounterType.P1P1));
        effect.setText("then sacrifice {this} unless you pay {1} for each +1/+1 counter on it.");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When Phantasmal Sphere leaves the battlefield, target opponent puts an X/X blue Orb creature token with flying onto the battlefield, where X is the number of +1/+1 counters on Phantasmal Sphere.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new PhantasmalSphereEffect(), false);
        ability2.addTarget(new TargetOpponent());
        this.addAbility(ability2);

    }

    private PhantasmalSphere(final PhantasmalSphere card) {
        super(card);
    }

    @Override
    public PhantasmalSphere copy() {
        return new PhantasmalSphere(this);
    }
}

class PhantasmalSphereEffect extends OneShotEffect {

    public PhantasmalSphereEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "target opponent creates an X/X blue Orb creature token "
                + "with flying, where X is the number "
                + "of +1/+1 counters on {this}";
    }

    public PhantasmalSphereEffect(final PhantasmalSphereEffect effect) {
        super(effect);
    }

    @Override
    public PhantasmalSphereEffect copy() {
        return new PhantasmalSphereEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (controller != null
                && targetOpponent != null) {
            Effect effect = new CreateTokenTargetEffect(new PhantasmalSphereToken(
                    new CountersSourceCount(CounterType.P1P1).calculate(
                            game, source, null)));
            effect.setTargetPointer(new FixedTarget(targetOpponent.getId()));
            effect.apply(game, source);
        }
        return false;
    }
}

class PhantasmalSphereToken extends TokenImpl {

    public PhantasmalSphereToken(int xValue) {
        super("Orb Token", "X/X blue Orb creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ORB);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(FlyingAbility.getInstance());
    }

    public PhantasmalSphereToken(final PhantasmalSphereToken token) {
        super(token);
    }

    public PhantasmalSphereToken copy() {
        return new PhantasmalSphereToken(this);
    }
}
