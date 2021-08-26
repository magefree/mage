package mage.cards.n;

import java.util.UUID;

import mage.abilities.common.AttacksOrBlocksAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeControllerAttachedEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author noahg
 */
public final class NettlingCurse extends CardImpl {

    public NettlingCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks or blocks, its controller loses 3 life.
        this.addAbility(new AttacksOrBlocksAttachedTriggeredAbility(new LoseLifeControllerAttachedEffect(3), AttachmentType.AURA));

        // {1}{R}: Enchanted creature attacks this turn if able.
        this.addAbility(new SimpleActivatedAbility(new AttacksIfAbleAttachedEffect(Duration.EndOfTurn, AttachmentType.AURA).setText("Enchanted creature attacks this turn if able."), new ManaCostsImpl("{1}{R}")));
    }

    private NettlingCurse(final NettlingCurse card) {
        super(card);
    }

    @Override
    public NettlingCurse copy() {
        return new NettlingCurse(this);
    }
}
