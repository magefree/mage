
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseLandTypeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class TravelersCloak extends CardImpl {

    public TravelersCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // As Traveler's Cloak enters the battlefield, choose a land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseLandTypeEffect(Outcome.AddAbility)));
        
        // When Traveler's Cloak enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
        
        // Enchanted creature has landwalk of the chosen type.
        FilterLandPermanent filter = new FilterLandPermanent("Landwalk of the chosen type");
        filter.add(ChosenSubtypePredicate.instance);
        Ability landwalkAbility = new LandwalkAbility(filter);
        Effect effect = new GainAbilityAttachedEffect(landwalkAbility, AttachmentType.AURA);
        effect.setText("Enchanted creature has landwalk of the chosen type");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public TravelersCloak(final TravelersCloak card) {
        super(card);
    }

    @Override
    public TravelersCloak copy() {
        return new TravelersCloak(this);
    }
}
