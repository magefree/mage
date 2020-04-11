package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;



/**
 *
 * @author drowinternet
 */
public final class BackForMore extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public BackForMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{G}");
        

        // Return target creature card from your graveyard to the battlefield. When you do, it fights up to one target creature you don't control.

        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights up to one target creature you don't control"));
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

    }

    private BackForMore(final BackForMore card) {
        super(card);
    }

    @Override
    public BackForMore copy() {
        return new BackForMore(this);
    }
}
