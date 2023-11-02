
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class StoneGiant extends CardImpl {

    public StoneGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        // {tap}: Target creature you control with toughness less than Stone Giant's power gains flying until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new TapSourceCost());
        ability.addTarget(new StoneGiantTarget());
        // Destroy that creature at the beginning of the next end step.
        ability.addEffect(new DestroyTargetAtBeginningOfNextEndStepEffect());
        this.addAbility(ability);
    }

    private StoneGiant(final StoneGiant card) {
        super(card);
    }

    @Override
    public StoneGiant copy() {
        return new StoneGiant(this);
    }
}

class StoneGiantTarget extends TargetPermanent {

    public StoneGiantTarget() {
        super(new FilterControlledCreaturePermanent("creature you control with toughness less than {this}'s power"));
    }

    private StoneGiantTarget(final StoneGiantTarget target) {
        super(target);
    }

    @Override
    public StoneGiantTarget copy() {
        return new StoneGiantTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent targetCreature = game.getPermanent(id);

        if (targetCreature != null && sourceCreature != null
                && targetCreature.getToughness().getValue() < sourceCreature.getPower().getValue()) {
            return super.canTarget(controllerId, id, source, game);
        }
        return false;
    }
}
