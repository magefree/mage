package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
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
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature loses all abilities and is a Bird creature with base power and toughness 1/1.
        // Setting the card type to Creature removes its other card types and their subtypes (CR 205.1a).
        this.addAbility(new SimpleStaticAbility(
                new BecomesCreatureAttachedEffect(new CreatureToken(1, 1, "1/1 Bird creature", SubType.BIRD),
                        "Enchanted creature loses all abilities and is a Bird creature with base power and toughness 1/1",
                        Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ALL_BUT_COLOR)));
    }

    private FowlPlay(final FowlPlay card) {
        super(card);
    }

    @Override
    public FowlPlay copy() {
        return new FowlPlay(this);
    }
}
