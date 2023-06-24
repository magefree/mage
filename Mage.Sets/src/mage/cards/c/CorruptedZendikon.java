
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class CorruptedZendikon extends CardImpl {

    public CorruptedZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land is a 3/3 black Ooze creature. It's still a land.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BecomesCreatureAttachedEffect(new CorruptedZendikonOozeToken(),
                        "Enchanted land is a 3/3 black Ooze creature. It's still a land.", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);

        // When enchanted land dies, return that card to its owner's hand.
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false);
        this.addAbility(ability3);
    }

    private CorruptedZendikon(final CorruptedZendikon card) {
        super(card);
    }

    @Override
    public CorruptedZendikon copy() {
        return new CorruptedZendikon(this);
    }
}

class CorruptedZendikonOozeToken extends TokenImpl {

    public CorruptedZendikonOozeToken() {
        super("Ooze", "3/3 black Ooze creature");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
    public CorruptedZendikonOozeToken(final CorruptedZendikonOozeToken token) {
        super(token);
    }

    public CorruptedZendikonOozeToken copy() {
        return new CorruptedZendikonOozeToken(this);
    }

}
