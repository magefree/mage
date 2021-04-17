
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class WaywardServant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final String rule = "Whenever another Zombie enters the battlefield under your control, each opponent loses 1 life and you gain 1 life.";

    public WaywardServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Zombie enters the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        Effect effect = new LoseLifeOpponentsEffect(1);
        Effect effect2 = new GainLifeEffect(1);
        Ability ability = new EntersBattlefieldAllTriggeredAbility(effect, filter, rule);
        ability.addEffect(effect2);
        this.addAbility(ability);

    }

    private WaywardServant(final WaywardServant card) {
        super(card);
    }

    @Override
    public WaywardServant copy() {
        return new WaywardServant(this);
    }
}
