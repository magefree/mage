package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverquillCommand extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SilverquillCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{B}");

        // Choose two —
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Target creature gets +3/+3 and gains flying until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                3, 3
        ).setText("target creature gets +3/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // • Return target creature card with mana value 2 or less from your graveyard to the battlefield.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode);

        // • Target player draws a card and loses 1 life.
        mode = new Mode(new DrawCardTargetEffect(1));
        mode.addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // • Target opponent sacrifices a creature.
        mode = new Mode(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                1, "Target opponent"
        ));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private SilverquillCommand(final SilverquillCommand card) {
        super(card);
    }

    @Override
    public SilverquillCommand copy() {
        return new SilverquillCommand(this);
    }
}
