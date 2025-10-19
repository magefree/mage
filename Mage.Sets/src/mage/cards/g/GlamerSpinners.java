package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlamerSpinners extends CardImpl {

    public GlamerSpinners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W/U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Glamer Spinners enters the battlefield, attach all Auras enchanting target permanent to another permanent with the same controller.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GlamerSpinnersEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private GlamerSpinners(final GlamerSpinners card) {
        super(card);
    }

    @Override
    public GlamerSpinners copy() {
        return new GlamerSpinners(this);
    }
}

class GlamerSpinnersEffect extends OneShotEffect {

    GlamerSpinnersEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "attach all Auras enchanting target permanent to another permanent with the same controller";
    }

    private GlamerSpinnersEffect(final GlamerSpinnersEffect effect) {
        super(effect);
    }

    @Override
    public GlamerSpinnersEffect copy() {
        return new GlamerSpinnersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null
                || targetPermanent
                        .getAttachments()
                        .stream()
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .noneMatch(p -> p.hasSubtype(SubType.AURA, game))) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent(
                "permanent" + Optional
                        .ofNullable(targetPermanent)
                        .map(Controllable::getControllerId)
                        .map(game::getPlayer)
                        .map(Player::getName)
                        .map(s -> " controlled by " + s)
                        .orElse("")
        );
        filter.add(new ControllerIdPredicate(targetPermanent.getControllerId()));
        filter.add(Predicates.not(new PermanentIdPredicate(targetPermanent.getId())));
        if (!game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        player.choose(Outcome.AIDontUseIt, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        // new list to avoid concurrent modification
        for (UUID attachmentId : new LinkedList<>(targetPermanent.getAttachments())) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.hasSubtype(SubType.AURA, game)) {
                permanent.addAttachment(attachmentId, source, game);
            }
        }
        return true;
    }
}
