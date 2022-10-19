package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarroomBrawl extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature the opponent to your left controls");

    static {
        filter.add(BarroomBrawlPredicate.instance);
    }

    public BarroomBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control fights target creature the opponent to your left controls. Then that player may copy this spell and may choose new targets for the copy.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addEffect(new BarroomBrawlEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private BarroomBrawl(final BarroomBrawl card) {
        super(card);
    }

    @Override
    public BarroomBrawl copy() {
        return new BarroomBrawl(this);
    }
}

enum BarroomBrawlPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().isControlledBy(
                game.getState()
                        .getPlayersInRange(input.getSource().getControllerId(), game)
                        .getNext()
        );
    }
}

class BarroomBrawlEffect extends OneShotEffect {

    BarroomBrawlEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new SecondTargetPointer());
        staticText = "Then that player may copy this spell and may choose new targets for the copy";
    }

    private BarroomBrawlEffect(final BarroomBrawlEffect effect) {
        super(effect);
    }

    @Override
    public BarroomBrawlEffect copy() {
        return new BarroomBrawlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = Optional.ofNullable(getTargetPointer()
                .getFirstTargetPermanentOrLKI(game, source))
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .orElse(null);
        Spell spell = game.getSpellOrLKIStack(source.getSourceId());
        if (player != null && spell != null && player.chooseUse(outcome, "Copy the spell?", source, game)) {
            spell.createCopyOnStack(game, source, player.getId(), true);
            return true;
        }
        return false;
    }
}
