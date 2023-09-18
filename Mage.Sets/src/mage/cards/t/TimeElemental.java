
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author L_J
 */
public final class TimeElemental extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent that isn't enchanted");

    static {
        filter.add(Predicates.not(EnchantedPredicate.instance));
    }

    public TimeElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // When Time Elemental attacks or blocks, at end of combat, sacrifice it and it deals 5 damage to you.
        DelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect().setText("at end of combat, sacrifice it"));
        ability.addEffect(new DamageControllerEffect(5).setText("and it deals 5 damage to you"));
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(ability, true), false));
        
        // {2}{U}{U}, {tap}: Return target permanent that isn't enchanted to its owner's hand.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{2}{U}{U}"));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetPermanent(filter));
        this.addAbility(ability2);
    }

    private TimeElemental(final TimeElemental card) {
        super(card);
    }

    @Override
    public TimeElemental copy() {
        return new TimeElemental(this);
    }
}
