package mage.cards.c;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoopedUp extends CardImpl {

    public CoopedUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)));

        // {2}{W}: Exile enchanted creature.
        this.addAbility(new SimpleActivatedAbility(new ExileAttachedEffect(), new ManaCostsImpl<>("{2}{W}")));
    }

    private CoopedUp(final CoopedUp card) {
        super(card);
    }

    @Override
    public CoopedUp copy() {
        return new CoopedUp(this);
    }
}
