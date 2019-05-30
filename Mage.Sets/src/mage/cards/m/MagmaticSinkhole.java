package mage.cards.m;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaticSinkhole extends CardImpl {

    public MagmaticSinkhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{R}");

        // Delve
        this.addAbility(new DelveAbility());

        // Magmatic Sinkhole deals 5 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private MagmaticSinkhole(final MagmaticSinkhole card) {
        super(card);
    }

    @Override
    public MagmaticSinkhole copy() {
        return new MagmaticSinkhole(this);
    }
}
