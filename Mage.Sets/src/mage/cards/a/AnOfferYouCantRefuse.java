package mage.cards.a;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnOfferYouCantRefuse extends CardImpl {

    public AnOfferYouCantRefuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target noncreature spell. Its controller creates two Treasure tokens.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(
                new TreasureToken(), 2, false, CreateTokenControllerTargetEffect.TargetKind.SPELL
        ));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private AnOfferYouCantRefuse(final AnOfferYouCantRefuse card) {
        super(card);
    }

    @Override
    public AnOfferYouCantRefuse copy() {
        return new AnOfferYouCantRefuse(this);
    }
}