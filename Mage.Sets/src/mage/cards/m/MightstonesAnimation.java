package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MightstonesAnimation extends CardImpl {

    public MightstonesAnimation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Mightstone's Animation enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Enchanted artifact is a creature with base power and toughness 4/4 in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(4, 4), "Enchanted artifact is a creature " +
                "with base power and toughness 4/4 in addition to its other types", Duration.WhileOnBattlefield
        )));
    }

    private MightstonesAnimation(final MightstonesAnimation card) {
        super(card);
    }

    @Override
    public MightstonesAnimation copy() {
        return new MightstonesAnimation(this);
    }
}
