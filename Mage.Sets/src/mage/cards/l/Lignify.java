
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Lignify extends CardImpl {

    public Lignify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature is a Treefolk with base power and toughness 0/4 and loses all abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BecomesCreatureAttachedEffect(new CreatureToken(0, 4, "0/4 Treefolk creature", SubType.TREEFOLK),
                        "Enchanted creature is a Treefolk with base power and toughness 0/4 and loses all abilities",
                        Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ABILITIES_SUBTYPE)));

    }

    private Lignify(final Lignify card) {
        super(card);
    }

    @Override
    public Lignify copy() {
        return new Lignify(this);
    }
}
