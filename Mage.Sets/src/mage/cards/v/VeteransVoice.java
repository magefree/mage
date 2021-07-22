
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class VeteransVoice extends CardImpl {

    private static final FilterCreaturePermanent filterUntapped = new FilterCreaturePermanent("enchanted creature is untapped");
    
    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    public VeteransVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Tap enchanted creature: Target creature other than the creature tapped this way gets +2/+1 until end of turn. Activate this ability only if enchanted creature is untapped.
        FilterPermanent filterTarget = new FilterCreaturePermanent("creature other than the creature tapped this way");
        filterTarget.add(Predicates.not(new AttachmentByUUIDPredicate(this.getId()))); 
        Ability ability2 = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(2, 1, Duration.EndOfTurn), new TapAttachedCost(), new AttachedToMatchesFilterCondition(filterUntapped));
        ability2.addTarget(new TargetPermanent(filterTarget));
        this.addAbility(ability2);
    }

    private VeteransVoice(final VeteransVoice card) {
        super(card);
    }

    @Override
    public VeteransVoice copy() {
        return new VeteransVoice(this);
    }
}

class AttachmentByUUIDPredicate implements Predicate<Permanent> {

    private final UUID id;

    public AttachmentByUUIDPredicate(UUID id) {
        this.id = id;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments().contains(id);            
    }

    @Override
    public String toString() {
        return "AttachmentUUID(" + id + ')';
    }
}
