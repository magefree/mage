
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author LevelX2
 */
public final class MizziumSkin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MizziumSkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target creature you control gets +0/+1 and gains hexproof until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new BoostTargetEffect(0,1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));

        // Overload {1}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility ability = new OverloadAbility(this, new BoostAllEffect(0,1, Duration.EndOfTurn,filter,false), new ManaCostsImpl("{1}{U}"));
        ability.addEffect(new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, filter));
        this.addAbility(ability);
    }

    public MizziumSkin(final MizziumSkin card) {
        super(card);
    }

    @Override
    public MizziumSkin copy() {
        return new MizziumSkin(this);
    }
}