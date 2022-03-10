
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author L_J
 */
public final class RelicBind extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RelicBind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Whenever enchanted artifact becomes tapped, choose one
        // — Relic Bind deals 1 damage to target player or planeswalker.
        Ability ability2 = new BecomesTappedAttachedTriggeredAbility(new DamageTargetEffect(1), "enchanted artifact");
        ability2.addTarget(new TargetPlayerOrPlaneswalker());
        // — Target player gains 1 life.
        Mode mode = new Mode(new GainLifeTargetEffect(1));
        mode.addTarget(new TargetPlayer());
        ability2.addMode(mode);

        this.addAbility(ability2);
    }

    private RelicBind(final RelicBind card) {
        super(card);
    }

    @Override
    public RelicBind copy() {
        return new RelicBind(this);
    }
}
