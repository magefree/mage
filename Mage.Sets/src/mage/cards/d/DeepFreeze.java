
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author TheElk801
 */
public final class DeepFreeze extends CardImpl {

    public DeepFreeze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has base power and toughness 0/4, has defender, loses all other abilities, and is a blue Wall in addition to its other colors and types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BecomesCreatureAttachedEffect(new DeepFreezeToken(),
                        "Enchanted creature has base power and toughness 0/4, has defender, loses all other abilities, and is a blue Wall in addition to its other colors and types",
                        Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ABILITIES)
        ));
    }

    private DeepFreeze(final DeepFreeze card) {
        super(card);
    }

    @Override
    public DeepFreeze copy() {
        return new DeepFreeze(this);
    }
}

class DeepFreezeToken extends TokenImpl {

    public DeepFreezeToken() {
        super("", "has base power and toughness 0/4, has defender, loses all other abilities, and is a blue Wall in addition to its other colors and types");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        color.addColor(ObjectColor.BLUE);
        power = new MageInt(0);
        toughness = new MageInt(4);

        this.addAbility(DefenderAbility.getInstance());
    }

    public DeepFreezeToken(final DeepFreezeToken token) {
        super(token);
    }

    public DeepFreezeToken copy() {
        return new DeepFreezeToken(this);
    }

}
