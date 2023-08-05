
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class FeastOfBlood extends CardImpl {

    public FeastOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Cast Feast of Blood only if you control two or more Vampires.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(
                new PermanentsOnTheBattlefieldCondition(
                        new FilterControlledCreaturePermanent(SubType.VAMPIRE, "you control two or more Vampires"),
                        ComparisonType.MORE_THAN, 1)));

        // Destroy target creature. You gain 4 life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private FeastOfBlood(final FeastOfBlood card) {
        super(card);
    }

    @Override
    public FeastOfBlood copy() {
        return new FeastOfBlood(this);
    }
}
