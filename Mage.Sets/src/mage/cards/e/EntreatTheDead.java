package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntreatTheDead extends CardImpl {

    public EntreatTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{B}{B}{B}");

        // Return X target creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return X target creature cards from your graveyard to the battlefield"));
        this.getSpellAbility().setTargetAdjuster(EntreatTheDeadAdjuster.instance);

        // Miracle {X}{B}{B}
        this.addAbility(new MiracleAbility("{X}{B}{B}"));
    }

    private EntreatTheDead(final EntreatTheDead card) {
        super(card);
    }

    @Override
    public EntreatTheDead copy() {
        return new EntreatTheDead(this);
    }
}

enum EntreatTheDeadAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        String filterName = xValue
                + (xValue != 1 ? " creature cards" : "creature card")
                + " from your graveyard";
        Target target = new TargetCardInYourGraveyard(
                xValue, new FilterCreatureCard(filterName)
        );
        ability.addTarget(target);
    }
}
