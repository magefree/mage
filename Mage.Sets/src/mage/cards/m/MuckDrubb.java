
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChangeATargetOfTargetSpellAbilityToSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * 
 * @author L_J
 */
public final class MuckDrubb extends CardImpl {

    protected static final FilterSpell filter = new FilterSpell("spell that targets only a single creature");

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    public MuckDrubb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Muck Drubb enters the battlefield, change the target of target spell that targets only a single creature to Muck Drubb.
        Effect effect = new ChangeATargetOfTargetSpellAbilityToSourceEffect();
        effect.setText("change the target of target spell that targets only a single creature to {this}");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        Target target = new TargetSpell(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        
        // Madness {2}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private MuckDrubb(final MuckDrubb card) {
        super(card);
    }

    @Override
    public MuckDrubb copy() {
        return new MuckDrubb(this);
    }
}