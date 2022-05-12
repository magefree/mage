package mage.cards.p;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Prizefight extends CardImpl {

    public Prizefight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // Create a Treasure token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("<br>"));
    }

    private Prizefight(final Prizefight card) {
        super(card);
    }

    @Override
    public Prizefight copy() {
        return new Prizefight(this);
    }
}
