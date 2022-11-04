package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RepairAndRecharge extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("artifact, enchantment, or planeswalker card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public RepairAndRecharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Return target artifact, enchantment, or planeswalker card from your graveyard to the battlefield. Create a tapped Powerstone token.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 1, true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private RepairAndRecharge(final RepairAndRecharge card) {
        super(card);
    }

    @Override
    public RepairAndRecharge copy() {
        return new RepairAndRecharge(this);
    }
}
