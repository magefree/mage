
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RimeTransfusion extends CardImpl {

    static final String rule = "and has \"{S}: This creature can't be blocked this turn except by snow creatures.\"";

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by snow creatures until end of turn");

    static {
        filter.add(Predicates.not(SuperType.SNOW.getPredicate()));
    }

    public RimeTransfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+1 and has "{S}: This creature can't be blocked this turn except by snow creatures."
        SimpleStaticAbility ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 1, Duration.WhileOnBattlefield));
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.EndOfTurn))),new ManaCostsImpl<>("{S}"));
        ability2.addEffect(new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield, rule));
        this.addAbility(ability2);
    }

    private RimeTransfusion(final RimeTransfusion card) {
        super(card);
    }

    @Override
    public RimeTransfusion copy() {
        return new RimeTransfusion(this);
    }
}
