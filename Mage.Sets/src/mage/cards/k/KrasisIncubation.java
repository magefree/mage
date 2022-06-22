
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KrasisIncubation extends CardImpl {

    public KrasisIncubation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseLife));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack or block, and its activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockAttackActivateAttachedEffect()));

        // {1}{G}{U}, Return Krasis Incubation to its owner's hand: Put two +1/+1 counters on enchanted creature.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddPlusOneCountersAttachedEffect(2), new ManaCostsImpl<>("{1}{G}{U}"));
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        this.addAbility(ability);

    }

    private KrasisIncubation(final KrasisIncubation card) {
        super(card);
    }

    @Override
    public KrasisIncubation copy() {
        return new KrasisIncubation(this);
    }
}
