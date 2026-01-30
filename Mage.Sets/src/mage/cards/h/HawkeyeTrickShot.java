package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class HawkeyeTrickShot extends CardImpl {

    private static final FilterControlledPermanent filter =
        new FilterControlledPermanent(SubType.HERO, "{this} or another Hero you control");

    public HawkeyeTrickShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Hawkeye or another Hero you control enters, it deals damage equal to
        // the number of Heroes you control to any target.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new HawkeyeTrickShotEffect(), filter, false, true
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HawkeyeTrickShot(final HawkeyeTrickShot card) {
        super(card);
    }

    @Override
    public HawkeyeTrickShot copy() {
        return new HawkeyeTrickShot(this);
    }
}

class HawkeyeTrickShotEffect extends OneShotEffect {

    private static final DynamicValue heroes = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.HERO));

    HawkeyeTrickShotEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to the number of Heroes you control to any target";
    }

    private HawkeyeTrickShotEffect(final HawkeyeTrickShotEffect effect) {
        super(effect);
    }

    @Override
    public HawkeyeTrickShotEffect copy() {
        return new HawkeyeTrickShotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent hero = (Permanent) getValue("permanentEnteringBattlefield");
        if (hero == null) {
            return false;
        }

        UUID target = source.getTargets().getFirstTarget();

        Permanent permanent = game.getPermanent(target);
        if (permanent != null) {
            return permanent.damage(heroes.calculate(game, source, this), hero.getId(), source, game, false, true) > 0;
        }
        Player player = game.getPlayer(target);
        if (player != null) {
            return player.damage(heroes.calculate(game, source, this), hero.getId(), source, game) > 0;
        }

        return false;
    }
}
