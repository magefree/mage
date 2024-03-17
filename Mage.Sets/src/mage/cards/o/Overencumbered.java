package mage.cards.o;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetOpponentPermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Overencumbered extends CardImpl {

    public Overencumbered(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant opponent
        TargetPermanent auraTarget = new TargetOpponentPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Overencumbered enters the battlefield, enchanted opponent creates a Clue token, a Food token, and a Junk token.
        // At the beginning of combat on enchanted opponent's turn, that player may pay {1} for each artifact they control. If they don't, creatures can't attack this combat.
    }

    private Overencumbered(final Overencumbered card) {
        super(card);
    }

    @Override
    public Overencumbered copy() {
        return new Overencumbered(this);
    }
}
