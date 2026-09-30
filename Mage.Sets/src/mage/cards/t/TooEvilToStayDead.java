package mage.cards.t;

import java.util.UUID;

import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

/**
 *
 * @author muz
 */
public final class TooEvilToStayDead extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card in your graveyard with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    public TooEvilToStayDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");


        // Teamwork 4
        this.addAbility(new TeamworkAbility(4));

        // Choose target creature card in your graveyard with mana value 4 or less. If this spell was cast using teamwork, instead choose target creature card in your graveyard. Return the chosen card to the battlefield.
        this.getSpellAbility().addEffect(new InfoEffect("Choose target creature card in your graveyard with mana value 4 or less. If this spell was cast using teamwork, instead choose target creature card in your graveyard."));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("Return the chosen card to the battlefield"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
            TeamworkCondition.instance,
            new TargetCardInYourGraveyard(filter),
            new TargetCardInYourGraveyard()
        ));
    }

    private TooEvilToStayDead(final TooEvilToStayDead card) {
        super(card);
    }

    @Override
    public TooEvilToStayDead copy() {
        return new TooEvilToStayDead(this);
    }
}
