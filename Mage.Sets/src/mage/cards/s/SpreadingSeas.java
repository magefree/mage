
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;
import mage.abilities.effects.ContinuousEffect;
import mage.constants.DependencyType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SpreadingSeas extends CardImpl {

    public SpreadingSeas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Spreading Seas enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // Enchanted land is an Island.
        ContinuousEffect effect = new BecomesBasicLandEnchantedEffect(SubType.ISLAND);
        effect.addDependencyType(DependencyType.BecomeIsland);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private SpreadingSeas(final SpreadingSeas card) {
        super(card);
    }

    @Override
    public SpreadingSeas copy() {
        return new SpreadingSeas(this);
    }
}
