package mage.cards.w;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class WondrousRevival extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Hero creature cards");

    static {
        filter.add(SubType.HERO.getPredicate());
    }

    public WondrousRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Return up to three target Hero creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 3, filter));
    }

    private WondrousRevival(final WondrousRevival card) {
        super(card);
    }

    @Override
    public WondrousRevival copy() {
        return new WondrousRevival(this);
    }
}
