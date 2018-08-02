package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class EntreatTheDead extends CardImpl {

    public EntreatTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{B}{B}{B}");

        // Return X target creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(1, StaticFilters.FILTER_CARD_CREATURE));

        // Miracle {X}{B}{B}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl("{X}{B}{B}")));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
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

    public EntreatTheDead(final EntreatTheDead card) {
        super(card);
    }

    @Override
    public EntreatTheDead copy() {
        return new EntreatTheDead(this);
    }
}
