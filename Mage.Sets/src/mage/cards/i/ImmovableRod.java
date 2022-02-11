package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImmovableRod extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ImmovableRod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        // You may choose not to untap Immovable Rod during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // Whenever Immovable Rod becomes untapped, venture into the dungeon.
        this.addAbility(new InspiredAbility(new VentureIntoTheDungeonEffect(), false, false));

        // {3}{W}, {T}: For as long as Immovable Rod remains tapped, another target permanent loses all abilities and can't attack or block.
        Ability ability = new SimpleActivatedAbility(new ImmovableRodAbilityEffect(), new ManaCostsImpl<>("{3}{W}"));
        ability.addEffect(new ImmovableRodAttackBlockTargetEffect());
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ImmovableRod(final ImmovableRod card) {
        super(card);
    }

    @Override
    public ImmovableRod copy() {
        return new ImmovableRod(this);
    }
}

class ImmovableRodAbilityEffect extends ContinuousEffectImpl {

    ImmovableRodAbilityEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Detriment);
        staticText = "for as long as {this} remains tapped, another target permanent loses all abilities";
    }

    private ImmovableRodAbilityEffect(final ImmovableRodAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || !sourcePermanent.isTapped() || permanent == null) {
            discard();
            return false;
        }
        permanent.removeAllAbilities(source.getSourceId(), game);
        return true;
    }

    @Override
    public ImmovableRodAbilityEffect copy() {
        return new ImmovableRodAbilityEffect(this);
    }
}

class ImmovableRodAttackBlockTargetEffect extends RestrictionEffect {

    ImmovableRodAttackBlockTargetEffect() {
        super(Duration.Custom);
        staticText = "and can't attack or block";
    }

    public ImmovableRodAttackBlockTargetEffect(final ImmovableRodAttackBlockTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || !sourcePermanent.isTapped()) {
            discard();
            return false;
        }
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public ImmovableRodAttackBlockTargetEffect copy() {
        return new ImmovableRodAttackBlockTargetEffect(this);
    }
}
