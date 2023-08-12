package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

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
        this.addAbility(new SimpleStaticAbility(new CantBlockAttackActivateAttachedEffect("permanent")));
    }

    private RealmbreakersGrasp(final RealmbreakersGrasp card) {
        super(card);
    }

    @Override
    public RealmbreakersGrasp copy() {
        return new RealmbreakersGrasp(this);
    }
}
