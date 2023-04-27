package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.permanent.token.SquirrelToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DruidsCall extends CardImpl {

    public DruidsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature is dealt damage, its controller creates that many 1/1 green Squirrel creature tokens.
        Effect effect = new CreateTokenTargetEffect(new SquirrelToken(), SavedDamageValue.MANY);
        effect.setText("its controller creates that many 1/1 green Squirrel creature tokens");
        this.addAbility(new DealtDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, effect, false, SetTargetPointer.PLAYER));
    }

    private DruidsCall(final DruidsCall card) {
        super(card);
    }

    @Override
    public DruidsCall copy() {
        return new DruidsCall(this);
    }
}
