package mage.cards.m;

import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class MisthiossFury extends CardImpl {

    public MisthiossFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Misthios's Fury deals 3 damage to target creature. If you control an Equipment, Misthios's Fury also deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageTargetControllerEffect(2),
                new YouControlPermanentCondition(StaticFilters.FILTER_PERMANENT_EQUIPMENT)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MisthiossFury(final MisthiossFury card) {
        super(card);
    }

    @Override
    public MisthiossFury copy() {
        return new MisthiossFury(this);
    }
}
