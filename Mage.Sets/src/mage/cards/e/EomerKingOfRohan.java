package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EomerKingOfRohan extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.HUMAN, "other Human you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EomerKingOfRohan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Eomer, King of Rohan enters the battlefield with a +1/+1 counter on it for each other Human you control.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        new PermanentsOnBattlefieldCount(filter),
                        true
                ), "with a +1/+1 counter on it for each other Human you control"
        ));

        // When Eomer enters the battlefield, target player becomes the monarch. Eomer deals damage equal to its power to any target.
        TriggeredAbility trigger = new EntersBattlefieldTriggeredAbility(
                new EomerKingOfRohanEffect()
        );
        trigger.addTarget(new TargetPlayer());
        trigger.addTarget(new TargetAnyTarget());

        this.addAbility(trigger);
    }

    private EomerKingOfRohan(final EomerKingOfRohan card) {
        super(card);
    }

    @Override
    public EomerKingOfRohan copy() {
        return new EomerKingOfRohan(this);
    }
}

class EomerKingOfRohanEffect extends OneShotEffect {

    EomerKingOfRohanEffect() {
        super(Outcome.Benefit);
        staticText = "target player becomes the monarch. {this} deals equal to its power to any target.";
    }

    private EomerKingOfRohanEffect(final EomerKingOfRohanEffect effect) {
        super(effect);
    }

    @Override
    public EomerKingOfRohanEffect copy() {
        return new EomerKingOfRohanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (player != null) {
            game.setMonarchId(source, player.getId());
        }

        Permanent eomer = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (eomer != null) {
            int power = eomer.getPower().getValue();
            if (power > 0) {
                game.damagePlayerOrPermanent(
                        source.getTargets().get(1).getFirstTarget(), power,
                        source.getSourceId(), source, game, false, true
                );
            }
        }

        return true;
    }
}