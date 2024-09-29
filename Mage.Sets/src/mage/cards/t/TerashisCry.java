package mage.cards.t;

import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */

public final class TerashisCry extends CardImpl {

    public TerashisCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");
        this.subtype.add(SubType.ARCANE);

        // Tap up to three target creatures.
        Target target = new TargetCreaturePermanent(0, 3);
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(target);
    }

    private TerashisCry(final TerashisCry card) {
        super(card);
    }

    @Override
    public TerashisCry copy() {
        return new TerashisCry(this);
    }

}
