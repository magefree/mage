package mage.cards.i;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanentSameController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class Incriminate extends CardImpl {

    public Incriminate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose two target creatures controlled by the same player. That player sacrifices one of them.
        this.getSpellAbility().addEffect(new IncriminateEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2));
    }

    private Incriminate(final Incriminate card) {
        super(card);
    }

    @Override
    public Incriminate copy() {
        return new Incriminate(this);
    }
}

class IncriminateEffect extends OneShotEffect {

    IncriminateEffect() {
        super(Outcome.Benefit);
        staticText = "choose two target creatures controlled by the same player. That player sacrifices one of them";
    }

    private IncriminateEffect(final IncriminateEffect effect) {
        super(effect);
    }

    @Override
    public IncriminateEffect copy() {
        return new IncriminateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Permanent permanent;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                permanent = permanents.get(0);
                break;
            case 2:
                Player player = game.getPlayer(permanents.get(0).getControllerId());
                if (player == null) {
                    return false;
                }
                FilterPermanent filter = new FilterPermanent("creature to sacrifice");
                filter.add(Predicates.or(
                        permanents
                                .stream()
                                .map(MageItem::getId)
                                .map(ControllerIdPredicate::new)
                                .collect(Collectors.toList())
                ));
                TargetPermanent target = new TargetPermanent(filter);
                target.setNotTarget(true);
                player.choose(Outcome.Sacrifice, target, source, game);
                permanent = game.getPermanent(target.getFirstTarget());
            default:
                throw new UnsupportedOperationException("This shouldn't have happened");
        }
        return permanent != null && permanent.sacrifice(source, game);
    }
}
