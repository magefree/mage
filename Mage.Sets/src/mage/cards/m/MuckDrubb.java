
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChangeATargetOfTargetSpellAbilityToSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.util.TargetAddress;

/**
 * 
 * @author L_J
 */
public final class MuckDrubb extends CardImpl {

    protected static final FilterSpell filter = new FilterSpell("spell that targets only a single creature");

    static {
        filter.add(new SpellWithOnlySingleTargetPredicate());
        filter.add(new TargetsPermanentPredicate(new FilterCreaturePermanent()));
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
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{2}{B}")));
    }

    private MuckDrubb(final MuckDrubb card) {
        super(card);
    }

    @Override
    public MuckDrubb copy() {
        return new MuckDrubb(this);
    }
}

class SpellWithOnlySingleTargetPredicate implements ObjectSourcePlayerPredicate<Spell> {

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        UUID singleTarget = null;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (singleTarget == null) {
                    singleTarget = targetId;
                } else if (!singleTarget.equals(targetId)) {
                    return false;
                }
            }
        }
        return singleTarget != null;
    }
}
