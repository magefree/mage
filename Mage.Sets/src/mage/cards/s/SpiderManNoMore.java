package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SpiderManNoMore extends CardImpl {

    public SpiderManNoMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature is a Citizen with base power and toughness 1/1. It has defender and loses all other abilities.
        Effect effect = new BecomesCreatureAttachedEffect(
                new CreatureToken(1, 1, "Citizen with base power and toughness 1/1")
                        .withSubType(SubType.CITIZEN)
                        .withAbility(DefenderAbility.getInstance()),
                "Enchanted creature is a Citizen with base power and toughness 1/1. It has defender and loses all other abilities",
                Duration.WhileOnBattlefield,
                BecomesCreatureAttachedEffect.LoseType.ABILITIES_SUBTYPE
        );
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private SpiderManNoMore(final SpiderManNoMore card) {
        super(card);
    }

    @Override
    public SpiderManNoMore copy() {
        return new SpiderManNoMore(this);
    }
}
