package mage.cards.d;

import mage.abilities.common.IsDealtDamageAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.SquirrelToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DruidsCall extends CardImpl {

    public DruidsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature is dealt damage, its controller creates that many 1/1 green Squirrel creature tokens.
        Effect effect = new CreateTokenTargetEffect(new SquirrelToken(), SavedDamageValue.MANY);
        effect.setText("its controller creates that many 1/1 green Squirrel creature tokens");
        this.addAbility(new IsDealtDamageAttachedTriggeredAbility(
                Zone.BATTLEFIELD, effect, false, "enchanted", SetTargetPointer.PLAYER
        ));
    }

    private DruidsCall(final DruidsCall card) {
        super(card);
    }

    @Override
    public DruidsCall copy() {
        return new DruidsCall(this);
    }
}
