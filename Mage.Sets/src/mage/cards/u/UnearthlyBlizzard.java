package mage.cards.u;

import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class UnearthlyBlizzard extends CardImpl {

    public UnearthlyBlizzard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");
        this.subtype.add(SubType.ARCANE);


        // Up to three target creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));

    }

    private UnearthlyBlizzard(final UnearthlyBlizzard card) {
        super(card);
    }

    @Override
    public UnearthlyBlizzard copy() {
        return new UnearthlyBlizzard(this);
    }
}
