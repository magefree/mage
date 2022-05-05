
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author BursegSardaukar
 */
public final class FodderLaunch extends CardImpl {

    public FodderLaunch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{3}{B}");
        this.subtype.add(SubType.GOBLIN);

        //As an additional cost to cast Fodder Launch, sacrifice a Goblin.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(SubType.GOBLIN, "a Goblin"), true)));

        //Target creature gets -5/-5 until end of turn. Fodder Launch deals 5 damage to that creature's controller.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(5).setText("{this} deals 5 damage to that creature's controller"));
    }

    private FodderLaunch(final FodderLaunch card) {
        super(card);
    }

    @Override
    public FodderLaunch copy() {
        return new FodderLaunch(this);
    }

}
