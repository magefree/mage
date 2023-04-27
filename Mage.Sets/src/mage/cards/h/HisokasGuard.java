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
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;


/**
 * @author nantuko
 */
public final class HisokasGuard extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control other than Hisoka's Guard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HisokasGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Hisoka's Guard during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {1}{U}, {T}: Target creature you control other than Hisoka's Guard has shroud for as long as Hisoka's Guard remains tapped. (It can't be the target of spells or abilities.)
        Ability ability = new SimpleActivatedAbility(new HisokasGuardGainAbilityTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HisokasGuard(final HisokasGuard card) {
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
                guardedCreature.addAbility(ability, source.getSourceId(), game);
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
