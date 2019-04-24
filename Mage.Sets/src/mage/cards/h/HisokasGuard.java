
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;


/**
 * @author nantuko
 */
public final class HisokasGuard extends CardImpl {

    public HisokasGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Hisoka's Guard during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {1}{U}, {T}: Target creature you control other than Hisoka's Guard has shroud for as long as Hisoka's Guard remains tapped. (It can't be the target of spells or abilities.)
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(new AnotherPredicate());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HisokasGuardGainAbilityTargetEffect(), new ManaCostsImpl("{1}{U}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public HisokasGuard(final HisokasGuard card) {
        super(card);
    }

    @Override
    public HisokasGuard copy() {
        return new HisokasGuard(this);
    }
}

class HisokasGuardGainAbilityTargetEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public HisokasGuardGainAbilityTargetEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Target creature you control other than {this} has shroud for as long as {this} remains tapped";
        this.ability = ShroudAbility.getInstance();
    }

    public HisokasGuardGainAbilityTargetEffect(final HisokasGuardGainAbilityTargetEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public HisokasGuardGainAbilityTargetEffect copy() {
        return new HisokasGuardGainAbilityTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        // remember the guarded creature
        Permanent guardedCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Permanent hisokasGuard = game.getPermanent(source.getSourceId());
        if (guardedCreature != null && hisokasGuard != null) {
            hisokasGuard.addConnectedCard("HisokasGuard", guardedCreature.getId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent hisokasGuard = game.getPermanent(source.getSourceId());
        if (hisokasGuard != null && !hisokasGuard.getConnectedCards("HisokasGuard").isEmpty()) {
            Permanent guardedCreature = game.getPermanent(hisokasGuard.getConnectedCards("HisokasGuard").get(0));
            if (guardedCreature != null && hisokasGuard.isTapped()) {
                guardedCreature.addAbility(ability, game);
                return true;
            } else {
                // if guard isn't tapped, the effect is no more valid
                if (!hisokasGuard.isTapped()) {
                    hisokasGuard.clearConnectedCards("HisokasGuard");
                }
            }
        }
        return false;
    }

}
