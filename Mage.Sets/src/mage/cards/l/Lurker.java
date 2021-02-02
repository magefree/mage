
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.condition.common.BlockedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 *
 * @author L_J
 */
public final class Lurker extends CardImpl {

    public Lurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lurker can't be the target of spells unless it attacked or blocked this turn.
        Effect effect = new ConditionalContinuousRuleModifyingEffect(
                new CantBeTargetedSourceEffect(new FilterSpell(), Duration.WhileOnBattlefield),
                new InvertCondition(new OrCondition(AttackedThisTurnSourceCondition.instance, BlockedThisTurnSourceCondition.instance)));
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect.setText("{this} can't be the target of spells unless it attacked or blocked this turn"));
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new BlockedThisTurnWatcher());
        this.addAbility(ability);
    }

    private Lurker(final Lurker card) {
        super(card);
    }

    @Override
    public Lurker copy() {
        return new Lurker(this);
    }
}
