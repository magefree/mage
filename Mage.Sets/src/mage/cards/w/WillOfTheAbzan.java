package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillOfTheAbzan extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature with the greatest power among creatures you control");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }

    public WillOfTheAbzan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Any number of target opponents each sacrifice a creature with the greatest power among creatures that player controls and lose 3 life.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "")
                .setText("any number of target opponents each sacrifice a creature " +
                        "with the greatest power among creatures that player controls"));
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3).setText("and lose 3 life"));
        this.getSpellAbility().addTarget(new TargetOpponent(0, Integer.MAX_VALUE, false));

        // * Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
    }

    private WillOfTheAbzan(final WillOfTheAbzan card) {
        super(card);
    }

    @Override
    public WillOfTheAbzan copy() {
        return new WillOfTheAbzan(this);
    }
}
