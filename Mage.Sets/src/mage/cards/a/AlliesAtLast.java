package mage.cards.a;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlliesAtLast extends CardImpl {

    public AlliesAtLast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Affinity for Allies
        this.addAbility(new AffinityAbility(AffinityType.ALLIES));

        // Up to two target creatures you control each deal damage equal to their power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(true));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 2).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent().setTargetTag(3));
    }

    private AlliesAtLast(final AlliesAtLast card) {
        super(card);
    }

    @Override
    public AlliesAtLast copy() {
        return new AlliesAtLast(this);
    }
}
