
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class WindZendikon extends CardImpl {

    public WindZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        // Enchanted land is a 2/2 blue Elemental creature with flying. It's still a land.
        // When enchanted land dies, return that card to its owner's hand.
        
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new WindZendikonElementalToken(), "Enchanted land is a 2/2 blue Elemental creature with flying. It's still a land",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);
        
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false);
        this.addAbility(ability3);
    }

    private WindZendikon(final WindZendikon card) {
        super(card);
    }

    @Override
    public WindZendikon copy() {
        return new WindZendikon(this);
    }

    class WindZendikonElementalToken extends TokenImpl {
        WindZendikonElementalToken() {
            super("", "2/2 blue Elemental creature with flying");
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            subtype.add(SubType.ELEMENTAL);
            power = new MageInt(2);
            toughness = new MageInt(2);
            addAbility(FlyingAbility.getInstance());
        }
        public WindZendikonElementalToken(final WindZendikonElementalToken token) {
            super(token);
        }

        public WindZendikonElementalToken copy() {
            return new WindZendikonElementalToken(this);
        }
    }
}
