package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.StaticHint;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MegatronDestructiveForce extends CardImpl {
    public MegatronDestructiveForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setRed(true);
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Whenever Megatron attacks, you may sacrifice another artifact. When you do, Megatron deals damage equal to the sacrificed artifact's mana value to target creature. If excess damage would be dealt to that creature this way, instead that damage is dealt to that creature's controller and you convert Megatron.
        this.addAbility(new AttacksTriggeredAbility(new MegatronDestructiveForceEffect()));
    }

    private MegatronDestructiveForce(final MegatronDestructiveForce card) {
        super(card);
    }

    @Override
    public MegatronDestructiveForce copy() {
        return new MegatronDestructiveForce(this);
    }
}

class MegatronDestructiveForceEffect extends OneShotEffect {

    MegatronDestructiveForceEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another artifact. When you do, {this} deals damage equal to the sacrificed artifact's mana value to target creature. If excess damage would be dealt to that creature this way, instead that damage is dealt to that creature's controller and you convert {this}.";
    }

    private MegatronDestructiveForceEffect(final MegatronDestructiveForceEffect effect) {
        super(effect);
    }

    @Override
    public MegatronDestructiveForceEffect copy() {
        return new MegatronDestructiveForceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
            0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_SHORT_TEXT, true
        );
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        int manaValue = Math.max(permanent.getManaValue(), 0);
        if (!permanent.sacrifice(source, game)) {
            return false;
        }

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
            new MegatronDestructiveForceReflexiveEffect(manaValue), false
        );
        ability.addHint(new StaticHint("Sacrificed artifact mana value: " + manaValue));
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class MegatronDestructiveForceReflexiveEffect extends OneShotEffect {

    private final int value;

    MegatronDestructiveForceReflexiveEffect(int value) {
        super(Outcome.Damage);
        staticText = "{this} deals damage equal to the sacrificed artifact's mana value to target " +
            "creature. If excess damage would be dealt to that creature this way, instead that damage " +
            "is dealt to that creature's controller and you convert {this}.";
        this.value = value;
    }

    private MegatronDestructiveForceReflexiveEffect(final MegatronDestructiveForceReflexiveEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public MegatronDestructiveForceReflexiveEffect copy() {
        return new MegatronDestructiveForceReflexiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }

        if (value < 1) {
            return false;
        }

        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        int lethal = permanent.getLethalDamage(source.getSourceId(), game);
        int excess = value - lethal;
        if (excess <= 0) {
            // no excess damage.
            permanent.damage(value, source.getSourceId(), source, game);
            return true;
        }

        // excess damage. dealing excess to controller's instead. And convert Megatron.
        permanent.damage(lethal, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(excess, source, game);
        }
        new TransformSourceEffect().apply(game, source);

        return true;
    }
}
