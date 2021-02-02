
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HuntTheHunter extends CardImpl {

    private static final FilterControlledCreaturePermanent filterControlledGreen = new FilterControlledCreaturePermanent("green creature you control");
    private static final FilterCreaturePermanent filterOpponentGreen = new FilterCreaturePermanent("green creature an opponent controls");

    static {
        filterControlledGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterOpponentGreen.add(TargetController.OPPONENT.getControllerPredicate());
        filterOpponentGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public HuntTheHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target green creature you control gets +2/+2 until end of turn. It fights target green creature an opponent controls.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(1, 1, filterControlledGreen, false));

        effect = new FightTargetsEffect();
        effect.setText("It fights target green creature an opponent controls");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(filterOpponentGreen);
        this.getSpellAbility().addTarget(target);
    }

    private HuntTheHunter(final HuntTheHunter card) {
        super(card);
    }

    @Override
    public HuntTheHunter copy() {
        return new HuntTheHunter(this);
    }
}
