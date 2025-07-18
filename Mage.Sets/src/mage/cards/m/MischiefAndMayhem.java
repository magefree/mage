package mage.cards.m;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MischiefAndMayhem extends CardImpl {

    public MischiefAndMayhem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Up to two target creatures each get +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private MischiefAndMayhem(final MischiefAndMayhem card) {
        super(card);
    }

    @Override
    public MischiefAndMayhem copy() {
        return new MischiefAndMayhem(this);
    }
}
