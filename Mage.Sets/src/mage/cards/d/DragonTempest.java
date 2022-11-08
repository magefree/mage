package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DragonTempest extends CardImpl {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("a creature with flying");

    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DragonTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever a creature with flying enters the battlefield under your control, it gains haste until the end of turn.
        Effect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("it gains haste until end of turn");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filterFlying, false, SetTargetPointer.PERMANENT, ""));

        // Whenever a Dragon enters the battlefield under your control, it deals X damage to any target, where X is the number of Dragons you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DragonTempestDamageEffect(),
                new FilterCreaturePermanent(SubType.DRAGON, "a Dragon"),
                false,
                SetTargetPointer.NONE,
                ""
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private DragonTempest(final DragonTempest card) {
        super(card);
    }

    @Override
    public DragonTempest copy() {
        return new DragonTempest(this);
    }
}

class DragonTempestDamageEffect extends OneShotEffect {

    private static final FilterControlledPermanent dragonFilter = new FilterControlledPermanent();

    static {
        dragonFilter.add(SubType.DRAGON.getPredicate());
    }

    public DragonTempestDamageEffect() {
        super(Outcome.Damage);
        staticText = "it deals X damage to any target, where X is the number of Dragons you control";
    }

    public DragonTempestDamageEffect(final DragonTempestDamageEffect effect) {
        super(effect);
    }

    @Override
    public DragonTempestDamageEffect copy() {
        return new DragonTempestDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent damageSource = (Permanent) getValue("permanentEnteringBattlefield");
            int amount = game.getBattlefield().countAll(dragonFilter, controller.getId(), game);
            if (amount > 0) {
                Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    targetCreature.damage(amount, damageSource.getId(), source, game, false, true);
                } else {
                    Player player = game.getPlayer(source.getTargets().getFirstTarget());
                    if (player != null) {
                        player.damage(amount, damageSource.getId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
