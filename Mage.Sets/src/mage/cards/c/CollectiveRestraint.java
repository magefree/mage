
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Simown
 */
public final class CollectiveRestraint extends CardImpl {

    public CollectiveRestraint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Domain - Creatures can't attack you unless their controller pays {X} for each creature they control that's attacking you, where X is the number of basic land types you control.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CollectiveRestraintPayManaToAttackAllEffect());
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability.addHint(DomainHint.instance));

    }

    private CollectiveRestraint(final CollectiveRestraint card) {
        super(card);
    }

    @Override
    public CollectiveRestraint copy() {
        return new CollectiveRestraint(this);
    }
}

class CollectiveRestraintPayManaToAttackAllEffect extends CantAttackYouUnlessPayAllEffect {

    CollectiveRestraintPayManaToAttackAllEffect() {
        super(Duration.WhileOnBattlefield, new ManaCostsImpl<>("{X}"));
        staticText = "Creatures can't attack you unless their controller pays {X} for each creature they control that's attacking you, where X is the number of basic land types among lands you control.";
    }

    CollectiveRestraintPayManaToAttackAllEffect(CollectiveRestraintPayManaToAttackAllEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        int domainValue = DomainValue.REGULAR.calculate(game, source, this);
        if (domainValue > 0) {
            return new ManaCostsImpl<>("{" + domainValue + '}');
        }
        return null;
    }

    @Override
    public CollectiveRestraintPayManaToAttackAllEffect copy() {
        return new CollectiveRestraintPayManaToAttackAllEffect(this);
    }

}
