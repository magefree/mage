package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatchUp extends CardImpl {

    public PatchUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Return up to three target creature cards with total mana value 3 or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new PatchUpTarget());
    }

    private PatchUp(final PatchUp card) {
        super(card);
    }

    @Override
    public PatchUp copy() {
        return new PatchUp(this);
    }
}

class PatchUpTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards with total mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    PatchUpTarget() {
        super(0, 2, filter, false);
    }

    private PatchUpTarget(final PatchUpTarget target) {
        super(target);
    }

    @Override
    public PatchUpTarget copy() {
        return new PatchUpTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null &&
                this.getTargets()
                        .stream()
                        .map(game::getCard)
                        .mapToInt(Card::getManaValue)
                        .sum() + card.getManaValue() <= 3;
    }
}
