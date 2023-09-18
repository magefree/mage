package mage.cards.h;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HorobisWhisper extends CardImpl {

    private static final FilterLandPermanent filterCondition = new FilterLandPermanent("Swamp");

    static {
        filterCondition.add(SubType.SWAMP.getPredicate());
    }

    public HorobisWhisper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // If you control a Swamp, destroy target nonblack creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DestroyTargetEffect(), 
                new PermanentsOnTheBattlefieldCondition(filterCondition),"If you control a Swamp, destroy target nonblack creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK).withChooseHint("destroy if you control a Swamp"));

        // Splice onto Arcane-Exile four cards from your graveyard.
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, new ExileFromGraveCost(new TargetCardInYourGraveyard(4,4, new FilterCard("cards")))));

    }

    private HorobisWhisper(final HorobisWhisper card) {
        super(card);
    }

    @Override
    public HorobisWhisper copy() {
        return new HorobisWhisper(this);
    }
}
