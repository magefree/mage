package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class LuminatePrimordial extends CardImpl {

    public LuminatePrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Luminate Primordial enters the battlefield, for each opponent, exile up to one target creature
        // that player controls and that player gains life equal to its power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LuminatePrimordialEffect(), false);
        ability.setTargetAdjuster(LuminatePrimordialAdjuster.instance);
        this.addAbility(ability);
    }

    private LuminatePrimordial(final LuminatePrimordial card) {
        super(card);
    }

    @Override
    public LuminatePrimordial copy() {
        return new LuminatePrimordial(this);
    }
}

enum LuminatePrimordialAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature from opponent " + opponent.getLogName());
                filter.add(new ControllerIdPredicate(opponentId));
                TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, filter, false);
                ability.addTarget(target);
            }
        }
    }
}

class LuminatePrimordialEffect extends OneShotEffect {

    LuminatePrimordialEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each opponent, exile up to one target creature that player controls and that player gains life equal to its power";
    }

    private LuminatePrimordialEffect(final LuminatePrimordialEffect effect) {
        super(effect);
    }

    @Override
    public LuminatePrimordialEffect copy() {
        return new LuminatePrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Permanent> permanents = source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<UUID, Integer> map = permanents
                .stream()
                .collect(Collectors.toMap(
                        Controllable::getControllerId,
                        permanent -> permanent.getPower().getValue()
                ));
        controller.moveCards(permanents, Zone.EXILED, source, game);
        for (Map.Entry<UUID, Integer> entry : map.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player != null && entry.getValue() > 0) {
                player.gainLife(entry.getValue(), game, source);
            }
        }
        return true;
    }
}
