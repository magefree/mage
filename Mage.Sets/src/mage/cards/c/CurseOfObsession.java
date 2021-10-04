package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfObsession extends CardImpl {

    public CurseOfObsession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's draw step, that player draws two additional cards.
        this.addAbility(new BeginningOfDrawTriggeredAbility(
                new DrawCardTargetEffect(2)
                        .setText("that player draws two additional cards"),
                TargetController.ENCHANTED, false
        ));

        // At the beginning of enchanted player's end step, that player discards their hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DiscardHandTargetEffect("that player"),
                TargetController.ENCHANTED, false
        ));
    }

    private CurseOfObsession(final CurseOfObsession card) {
        super(card);
    }

    @Override
    public CurseOfObsession copy() {
        return new CurseOfObsession(this);
    }
}
