package mage.cards.b;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BackInTown extends CardImpl {
    private static final FilterCard filter = new FilterCreatureCard("outlaw cards");

    static {
        filter.add(OutlawPredicate.instance);
    }

    public BackInTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{B}");

        // Return X target outlaw creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return X target outlaw creature cards from your graveyard to the battlefield"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().setTargetAdjuster(new TargetsCountAdjuster(ManacostVariableValue.REGULAR));
    }

    private BackInTown(final BackInTown card) {
        super(card);
    }

    @Override
    public BackInTown copy() {
        return new BackInTown(this);
    }
}
