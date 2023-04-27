package mage.cards.h;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HurloonBattleHymn extends CardImpl {

    public HurloonBattleHymn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Kicker {W}
        this.addAbility(new KickerAbility("{W}"));

        // Hurloon Battle Hymn deals 4 damage to target creature or planeswalker. If this spell was kicked, you gain 4 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(4), KickedCondition.ONCE));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private HurloonBattleHymn(final HurloonBattleHymn card) {
        super(card);
    }

    @Override
    public HurloonBattleHymn copy() {
        return new HurloonBattleHymn(this);
    }
}
