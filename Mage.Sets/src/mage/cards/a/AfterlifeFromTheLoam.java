package mage.cards.a;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class AfterlifeFromTheLoam extends CardImpl {

    public AfterlifeFromTheLoam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}{B}");
        

        // Delve
        this.addAbility(new DelveAbility(false));

        // For each player, choose up to one target creature card in that player's graveyard. Put those cards onto the battlefield under your control. They're Zombies in addition to their other types.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("For each player, choose up to one target creature card in that player's graveyard. Put those cards onto the battlefield under your control"));
        this.getSpellAbility().addEffect(new AddCardSubTypeTargetEffect(SubType.ZOMBIE, Duration.WhileOnBattlefield)
                .setTargetPointer(new EachTargetPointer())
                .setText("they're Zombies in addition to their other types"));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(true, false));
    }

    private AfterlifeFromTheLoam(final AfterlifeFromTheLoam card) {
        super(card);
    }

    @Override
    public AfterlifeFromTheLoam copy() {
        return new AfterlifeFromTheLoam(this);
    }
}
