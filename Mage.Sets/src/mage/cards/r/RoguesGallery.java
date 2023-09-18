package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorAssignment;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoguesGallery extends CardImpl {

    public RoguesGallery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // For each color, return up to one target creature card of that color from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setText("for each color, return up to one target creature card " +
                        "of that color from your graveyard to your hand"));
        this.getSpellAbility().addTarget(new RoguesGalleryTarget());
    }

    private RoguesGallery(final RoguesGallery card) {
        super(card);
    }

    @Override
    public RoguesGallery copy() {
        return new RoguesGallery(this);
    }
}

class RoguesGalleryTarget extends TargetCardInYourGraveyard {

    private static final ColorAssignment colorAssigner = new ColorAssignment("W", "U", "B", "R", "G");

    private static final FilterCard filter = new FilterCreatureCard("a creature card of each color");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    RoguesGalleryTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private RoguesGalleryTarget(final RoguesGalleryTarget target) {
        super(target);
    }

    @Override
    public RoguesGalleryTarget copy() {
        return new RoguesGalleryTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return colorAssigner.getRoleCount(cards, game) >= cards.size();
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, null, game));
        return possibleTargets;
    }
}
