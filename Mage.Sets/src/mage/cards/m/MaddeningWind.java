package mage.cards.m;

import java.util.UUID;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author Cguy7777
 */
public final class MaddeningWind extends CardImpl {

    public MaddeningWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Cumulative upkeep {G}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{G}")));

        // At the beginning of the upkeep of enchanted creature's controller, Maddening Wind deals 2 damage to that player.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("{this} deals 2 damage to that player");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect,
                TargetController.CONTROLLER_ATTACHED_TO, false, true));
    }

    private MaddeningWind(final MaddeningWind card) {
        super(card);
    }

    @Override
    public MaddeningWind copy() {
        return new MaddeningWind(this);
    }
}
