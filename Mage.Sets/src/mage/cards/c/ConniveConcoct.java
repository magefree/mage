package mage.cards.c;

import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class ConniveConcoct extends SplitCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ConniveConcoct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U/B}{U/B}", "{3}{U}{B}", SpellAbilityType.SPLIT);

        // Connive
        // Gain control of target creature with power 2 or less.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new GainControlTargetEffect(Duration.Custom)
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetPermanent(filter)
        );

        // Concoct
        // Surveil 3, then return a creature card from your graveyard to the battlefield.
        this.getRightHalfCard().getSpellAbility().addEffect(new SurveilEffect(3, false));
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(
                false, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.BATTLEFIELD).concatBy(", then")
        );
    }

    private ConniveConcoct(final ConniveConcoct card) {
        super(card);
    }

    @Override
    public ConniveConcoct copy() {
        return new ConniveConcoct(this);
    }
}
