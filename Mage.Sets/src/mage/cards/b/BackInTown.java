package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BackInTown extends CardImpl {

    public BackInTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{B}");

        // Return X target outlaw creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return X target outlaw creature cards from your graveyard to the battlefield"));
        this.getSpellAbility().setTargetAdjuster(BackInTownAdjuster.instance);
    }

    private BackInTown(final BackInTown card) {
        super(card);
    }

    @Override
    public BackInTown copy() {
        return new BackInTown(this);
    }
}

enum BackInTownAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCard filter = new FilterCard("outlaw cards");

    static {
        filter.add(OutlawPredicate.instance);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(ability.getManaCostsToPay().getX(), filter));
    }
}
