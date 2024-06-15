package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RealmbreakersGrasp extends CardImpl {

    public RealmbreakersGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent can't attack or block, and its activated abilities can't be activated unless they're mana abilities.
        Ability ability = new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)
                .setText("Enchanted permanent can't attack or block,"));
        ability.addEffect(new RealmbreakersGraspEffect());
        this.addAbility(ability);
    }

    private RealmbreakersGrasp(final RealmbreakersGrasp card) {
        super(card);
    }

    @Override
    public RealmbreakersGrasp copy() {
        return new RealmbreakersGrasp(this);
    }
}

class RealmbreakersGraspEffect extends ContinuousRuleModifyingEffectImpl {

    RealmbreakersGraspEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "and its activated abilities can't be activated unless they're mana abilities";
    }

    private RealmbreakersGraspEffect(final RealmbreakersGraspEffect effect) {
        super(effect);
    }

    @Override
    public RealmbreakersGraspEffect copy() {
        return new RealmbreakersGraspEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.isAttachedTo(event.getSourceId())) {
            Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
            return ability.isPresent() && ability.get().isNonManaActivatedAbility();

        }
        return false;
    }
}
