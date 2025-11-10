package mage.cards.a;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class ArcTrail extends CardImpl {

    private static final FilterPermanentOrPlayer filter1 = new FilterAnyTarget("creature, player or planeswalker to deal 2 damage");
    private static final FilterPermanentOrPlayer filter2 = new FilterAnyTarget("another creature, player or planeswalker to deal 1 damage");

    static {
        filter2.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter2.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public ArcTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Arc Trail deals 2 damage to any target and 1 damage to another target
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(2, 1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter1).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter2).setTargetTag(2));
    }

    private ArcTrail(final ArcTrail card) {
        super(card);
    }

    @Override
    public ArcTrail copy() {
        return new ArcTrail(this);
    }

}
