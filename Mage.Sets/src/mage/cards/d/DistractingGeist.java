package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DistractingGeist extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public DistractingGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{2}{W}",
                "Clever Distraction",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "W");

        // Distracting Geist
        this.getLeftHalfCard().setPT(2, 1);

        // Whenever Distracting Geist attacks, tap target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Disturb {4}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{4}{W}"));

        // Clever Distraction

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new mage.abilities.effects.common.AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature attacks, tap target creature defending player controls."
        Ability ability2 = new AttacksTriggeredAbility(new TapTargetEffect()).setTriggerPhrase("Whenever this creature attacks, ");
        ability2.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(new mage.abilities.common.SimpleStaticAbility(new mage.abilities.effects.common.continuous.GainAbilityAttachedEffect(ability2, AttachmentType.AURA)));

        // If Clever Distracting would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private DistractingGeist(final DistractingGeist card) {
        super(card);
    }

    @Override
    public DistractingGeist copy() {
        return new DistractingGeist(this);
    }
}
