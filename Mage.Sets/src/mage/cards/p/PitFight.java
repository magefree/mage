package mage.cards.p;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2;

/**
 * @author LevelX2
 */
public final class PitFight extends CardImpl {

    public PitFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R/G}");

        // Target creature you control fights another target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
    }

    private PitFight(final PitFight card) {
        super(card);
    }

    @Override
    public PitFight copy() {
        return new PitFight(this);
    }
}
