package mage.cards.f;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangedFlames extends CardImpl {

    public FangedFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Fanged Flames deals 4 damage to target creature or planeswalker. If that creature or planeswalker would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect("creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FangedFlames(final FangedFlames card) {
        super(card);
    }

    @Override
    public FangedFlames copy() {
        return new FangedFlames(this);
    }
}
