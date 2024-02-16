package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;

public final class BoomBust extends SplitCard {

    private static final FilterLandPermanent filter1 = new FilterLandPermanent("land you control");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("land you don't control");

    static {
        filter1.add(TargetController.YOU.getControllerPredicate());
        filter2.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public BoomBust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}", "{5}{R}", SpellAbilityType.SPLIT);

        // Boom
        // Destroy target land you control and target land you don't control.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer()));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter1));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Bust
        // Destroy all lands.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS));

    }

    private BoomBust(final BoomBust card) {
        super(card);
    }

    @Override
    public BoomBust copy() {
        return new BoomBust(this);
    }
}
