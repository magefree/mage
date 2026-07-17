package mage.cards.l;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class LeonardosTechnique extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public LeonardosTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Sneak {1}{W}
        this.addAbility(new SneakAbility(this, "{1}{W}"));

        // Return one or two target creature cards each with mana value 3 or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(1, 2, filter));
    }

    private LeonardosTechnique(final LeonardosTechnique card) {
        super(card);
    }

    @Override
    public LeonardosTechnique copy() {
        return new LeonardosTechnique(this);
    }
}
