
package mage.cards.t;

import java.util.UUID;
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
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class TezzeretsTouch extends CardImpl {

    public TezzeretsTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new CreatureToken(5, 5, "5/5 creature"),"Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types", Duration.WhileOnBattlefield)));

        // When enchanted artifact is put into a graveyard, return that card to its owner's hand.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted artifact", false, false));
    }

    private TezzeretsTouch(final TezzeretsTouch card) {
        super(card);
    }

    @Override
    public TezzeretsTouch copy() {
        return new TezzeretsTouch(this);
    }
}
