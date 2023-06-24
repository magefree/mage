package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BrudicladTelchorMyrToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class BrudicladTelchorEngineer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BrudicladTelchorEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creature tokens you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));

        // At the beginning of combat on your turn, create a 2/1 blue Myr artifact creature token. Then you may choose a token you control. If you do, each other token you control becomes a copy of that token.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new BrudicladTelchorEngineerEffect(), TargetController.YOU, false));
    }

    private BrudicladTelchorEngineer(final BrudicladTelchorEngineer card) {
        super(card);
    }

    @Override
    public BrudicladTelchorEngineer copy() {
        return new BrudicladTelchorEngineer(this);
    }
}

class BrudicladTelchorEngineerEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BrudicladTelchorEngineerEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "create a 2/1 blue Phyrexian Myr artifact creature token. Then you may choose a token you control. If you do, each other token you control becomes a copy of that token";
    }

    public BrudicladTelchorEngineerEffect(final BrudicladTelchorEngineerEffect effect) {
        super(effect);
    }

    @Override
    public BrudicladTelchorEngineerEffect copy() {
        return new BrudicladTelchorEngineerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller == null){
            return false;
        }
        CreateTokenEffect effect = new CreateTokenEffect(new BrudicladTelchorMyrToken(), 1);

        if (effect.apply(game, source)) {
            TargetControlledPermanent target = new TargetControlledPermanent(0, 1, filter, true);
            target.setNotTarget(true);
            if (controller.chooseUse(outcome, "Select a token to copy?", source, game)
                    && controller.choose(Outcome.Neutral, target, source, game)) {
                Permanent toCopyFromPermanent = game.getPermanent(target.getFirstTarget());

                if (toCopyFromPermanent != null) {
                    for (Permanent toCopyToPermanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                        if (!toCopyToPermanent.equals(toCopyFromPermanent)) {
                            game.copyPermanent(toCopyFromPermanent, toCopyToPermanent.getId(), source, new EmptyCopyApplier());
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
