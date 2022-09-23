package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class RiveteersConfluence extends CardImpl {

    private static final FilterPermanent damageFilter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        damageFilter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public RiveteersConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}{G}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        //• You draw a card and you lose 1 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        //• Riveteers Confluence deals 1 damage to each creature and planeswalker you don’t control.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(1, damageFilter)));

        //• You may put a land card from your hand or graveyard onto the battlefield tapped.
        this.getSpellAbility().addMode(new Mode(new PutCardFromOneOfTwoZonesOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, true)));
    }

    private RiveteersConfluence(final RiveteersConfluence card) {
        super(card);
    }

    @Override
    public RiveteersConfluence copy() {
        return new RiveteersConfluence(this);
    }
}