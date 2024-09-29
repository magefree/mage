package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LegionLeadership extends ModalDoubleFacedCard {

    public LegionLeadership(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{R/W}",
                "Legion Stronghold", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Legion Leadership
        // Instant

        // Until end of turn, double target creature's power and it gains first strike.
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().getSpellAbility().addEffect(new LegionLeadershipTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                        .setText("and it gains first strike")
        );

        // 2.
        // Legion Stronghold
        // Land

        // Legion Stronghold enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {W}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private LegionLeadership(final LegionLeadership card) {
        super(card);
    }

    @Override
    public LegionLeadership copy() {
        return new LegionLeadership(this);
    }
}

class LegionLeadershipTargetEffect extends OneShotEffect {

    LegionLeadershipTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Until end of turn, double target creature's power";
    }

    private LegionLeadershipTargetEffect(final LegionLeadershipTargetEffect effect) {
        super(effect);
    }

    @Override
    public LegionLeadershipTargetEffect copy() {
        return new LegionLeadershipTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        ContinuousEffect boost = new BoostTargetEffect(permanent.getPower().getValue(), 0, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(boost, source);
        return true;
    }
}
