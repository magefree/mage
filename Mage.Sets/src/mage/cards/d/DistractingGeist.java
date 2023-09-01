package mage.cards.d;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
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

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public DistractingGeist(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{2}{W}",
                "Clever Distraction",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "W"
        );
        this.getLeftHalfCard().setPT(2, 1);

        // Whenever Distracting Geist attacks, tap target creature defending player controls.
        TriggeredAbility ability = new AttacksTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Disturb {4}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{4}{W}"));

        // Clever Distraction
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature attacks, tap target creature defending player controls."
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ability.copy().setTriggerPhrase("Whenever this creature attacks, "), AttachmentType.AURA
        )));

        // If Clever Distracting would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeExileAbility());
    }

    private DistractingGeist(final DistractingGeist card) {
        super(card);
    }

    @Override
    public DistractingGeist copy() {
        return new DistractingGeist(this);
    }
}
