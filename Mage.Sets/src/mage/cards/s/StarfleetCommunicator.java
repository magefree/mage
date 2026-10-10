package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author muz
 */
public final class StarfleetCommunicator extends CardImpl {

    public StarfleetCommunicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.COMMUNICATOR);
        this.subtype.add(SubType.EQUIPMENT);

        // When you cast this spell, copy it.
        this.addAbility(new CastSourceTriggeredAbility(new CopySourceSpellEffect().setText("copy it")));

        // Equipped creature has "Whenever this creature attacks, draw X cards, then you may put up to X land cards from your hand onto the battlefield tapped, where X is the number of other creatures you control with a Communicator attached to them."
        this.addAbility(new AttacksAttachedTriggeredAbility(
            new StarfleetCommunicatorEffect(),
            AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private StarfleetCommunicator(final StarfleetCommunicator card) {
        super(card);
    }

    @Override
    public StarfleetCommunicator copy() {
        return new StarfleetCommunicator(this);
    }
}

class StarfleetCommunicatorEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a Communicator attached");

    static {
        filter.add(CommunicatorAttachedPredicate.instance);
    }

    StarfleetCommunicatorEffect() {
        super(Outcome.Benefit);
        staticText = "draw X cards, then you may put up to X land cards from your hand onto the battlefield tapped, " +
            "where X is the number of other creatures you control with a Communicator attached to them";
    }

    private StarfleetCommunicatorEffect(final StarfleetCommunicatorEffect effect) {
        super(effect);
    }

    @Override
    public StarfleetCommunicatorEffect copy() {
        return new StarfleetCommunicatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        UUID attackingCreatureId = getTargetPointer().getFirst(game, source);
        int count = game.getBattlefield().getAllActivePermanents(
                filter, controller.getId(), game
            ).stream()
            .filter(creature -> !creature.getId().equals(attackingCreatureId))
            .mapToInt(creature -> 1)
            .sum();

        if (count > 0) {
            controller.drawCards(count, source, game);
            TargetCard targetCard = new TargetCardInHand(0, count, StaticFilters.FILTER_CARD_LANDS);
            controller.choose(outcome, controller.getHand(), targetCard, source, game);
            Cards cards = new CardsImpl(targetCard.getTargets());
            controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return true;
    }
}

enum CommunicatorAttachedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent creature, Game game) {
        return creature.getAttachments().stream()
            .map(game::getPermanent)
            .anyMatch(attachment -> attachment != null && attachment.hasSubtype(SubType.COMMUNICATOR, game));
    }

    @Override
    public String toString() {
        return "has a Communicator attached";
    }
}
