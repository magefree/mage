
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class GravenAbomination extends CardImpl {

    private final UUID originalId;

    private final static String rule = "Whenever {this} attacks, exile target card from defending player's graveyard.";

    public GravenAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Graven Abomination attacks, exile target card from defending player's graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect(), false, rule);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
        originalId = ability.getOriginalId();

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID gravenAbominationId = ability.getSourceId();
        FilterCard filter = new FilterCard("target card from defending player's graveyard");
        if (ability.getOriginalId().equals(originalId)) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(gravenAbominationId, game);
            Player defendingPlayer = game.getPlayer(defendingPlayerId);
            if (defendingPlayer != null) {
                filter.add(new OwnerIdPredicate(defendingPlayerId));
                ability.getTargets().clear();
                ability.getTargets().add(new TargetCardInGraveyard(filter));
            }
        }
    }

    public GravenAbomination(final GravenAbomination card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public GravenAbomination copy() {
        return new GravenAbomination(this);
    }
}
