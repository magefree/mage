package mage.cards.d;

import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DetentionVortex extends CardImpl {

    public DetentionVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant nonland permanent
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent can't attack or block, and its activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(new CantBlockAttackActivateAttachedEffect("permanent")));

        // {3}: Destroy Detention Vortex. Only your opponents may activate this ability and only as a sorcery.
        ActivatedAbility ability = new ActivateAsSorceryActivatedAbility(
                new DestroySourceEffect(), new GenericManaCost(3)
        );
        ability.setMayActivate(TargetController.OPPONENT);
        this.addAbility(ability);
    }

    private DetentionVortex(final DetentionVortex card) {
        super(card);
    }

    @Override
    public DetentionVortex copy() {
        return new DetentionVortex(this);
    }
}
