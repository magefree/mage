
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;

/**
 *
 * @author L_J
 */
public final class BronzeHorse extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    static {
        filter.add(AnotherPredicate.instance);
    }

    public BronzeHorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT, CardType.CREATURE},"{7}");
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // As long as you control another creature, prevent all damage that would be dealt to Bronze Horse by spells that target it.
        Effect effect = new ConditionalReplacementEffect(new PreventDamageToSourceBySpellsThatTargetIt(), new PermanentsOnTheBattlefieldCondition(filter));
        effect.setText("As long as you control another creature, prevent all damage that would be dealt to {this} by spells that target it.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BronzeHorse(final BronzeHorse card) {
        super(card);
    }

    @Override
    public BronzeHorse copy() {
        return new BronzeHorse(this);
    }
}

class PreventDamageToSourceBySpellsThatTargetIt extends PreventAllDamageToSourceEffect {

    public PreventDamageToSourceBySpellsThatTargetIt() {
        super(Duration.WhileOnBattlefield);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                Spell spell = game.getStack().getSpell(event.getSourceId());
                if (spell != null) {
                    for (UUID modeId : spell.getStackAbility().getModes().getSelectedModes()) {
                        Mode mode = spell.getStackAbility().getModes().get(modeId);
                        for (Target target : mode.getTargets()) {
                            for (UUID targetId : target.getTargets()) {
                                if (targetId.equals(source.getSourceId())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
