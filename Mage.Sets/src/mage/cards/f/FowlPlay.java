
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.constants.Outcome;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class FowlPlay extends CardImpl {

    public FowlPlay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature 
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature is a Chicken with base power and toughness 1/1 and loses all abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BecomesCreatureAttachedEffect(new CreatureToken(1, 1, "1/1 Chicken creature", SubType.CHICKEN),
                        "Enchanted creature is a Chicken with base power and toughness 1/1 and loses all abilities",
                        Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ABILITIES_SUBTYPE)));
    }

    public FowlPlay(final FowlPlay card) {
        super(card);
    }

    @Override
    public FowlPlay copy() {
        return new FowlPlay(this);
    }
}
