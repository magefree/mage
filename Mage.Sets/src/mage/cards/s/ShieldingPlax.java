
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeTargetedAttachedEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Wehk
 */
public final class ShieldingPlax extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("spells or abilities your opponents control");

    public ShieldingPlax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G/U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Shielding Plax enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Enchanted creature can't be the target of spells or abilities your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantBeTargetedAttachedEffect(filter, Duration.WhileOnBattlefield, AttachmentType.AURA, TargetController.OPPONENT)));
    }

    private ShieldingPlax(final ShieldingPlax card) {
        super(card);
    }

    @Override
    public ShieldingPlax copy() {
        return new ShieldingPlax(this);
    }
}
