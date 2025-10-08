package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BeholdTheSinisterSix extends CardImpl {

    public BeholdTheSinisterSix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Return up to six target creature cards with different names from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new BeholdTheSinisterSixTarget());
    }

    private BeholdTheSinisterSix(final BeholdTheSinisterSix card) {
        super(card);
    }

    @Override
    public BeholdTheSinisterSix copy() {
        return new BeholdTheSinisterSix(this);
    }
}

class BeholdTheSinisterSixTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterPermanentCard("creature cards with different names");

    BeholdTheSinisterSixTarget() {
        super(0, 6, filter, false);
    }

    private BeholdTheSinisterSixTarget(final BeholdTheSinisterSixTarget target) {
        super(target);
    }

    @Override
    public BeholdTheSinisterSixTarget copy() {
        return new BeholdTheSinisterSixTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Set<String> names = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Card card = game.getCard(id);
        return card != null && (this.getTargets().contains(id) || !names.contains(card.getName()));
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<String> names = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null && names.contains(card.getName());
        });
        return possibleTargets;
    }
}
