package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class RhinosRampage extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RhinosRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R/G}");
        

        // Target creature you control gets +1/+0 until end of turn. It fights target creature an opponent controls. When excess damage is dealt to the creature an opponent controls this way, destroy up to one target noncreature artifact with mana value 3 or less.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("It fights target creature an opponent controls"));
        this.getSpellAbility().addEffect(new RhinosRampageEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private RhinosRampage(final RhinosRampage card) {
        super(card);
    }

    @Override
    public RhinosRampage copy() {
        return new RhinosRampage(this);
    }
}

class RhinosRampageEffect extends OneShotEffect {

    RhinosRampageEffect() {
        super(Outcome.BoostCreature);
        staticText = "When excess damage is dealt to the creature an opponent controls this way, destroy up to one target noncreature " +
                "artifact with mana value 3 or less";
    }

    protected RhinosRampageEffect(final RhinosRampageEffect effect) {
        super(effect);
    }

    @Override
    public RhinosRampageEffect copy() {
        return new RhinosRampageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getTargets().get(1).getFirstTarget());
        if (permanent == null || permanent.getDamage() <= permanent.getToughness().getBaseValue()) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactPermanent("noncreature artifact with mana value 3 or less");
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
