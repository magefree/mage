package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author L_J
 */
public final class VeteransVoice extends CardImpl {

    private static final FilterPermanent filterUntapped = new FilterCreaturePermanent("enchanted creature is untapped");

    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filterUntapped);

    public VeteransVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Tap enchanted creature: Target creature other than the creature tapped this way gets +2/+1 until end of turn. Activate this ability only if enchanted creature is untapped.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(2, 1, Duration.EndOfTurn)
                        .setText("target creature other than the creature tapped this way gets +2/+1 until end of turn"),
                new TapAttachedCost(), condition
        ).setTargetAdjuster(VeteransVoiceAdjuster.instance));
    }

    private VeteransVoice(final VeteransVoice card) {
        super(card);
    }

    @Override
    public VeteransVoice copy() {
        return new VeteransVoice(this);
    }
}

enum VeteransVoiceAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent permanent = ability.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new PermanentIdPredicate(permanent.getAttachedTo())));
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(filter));
    }
}
