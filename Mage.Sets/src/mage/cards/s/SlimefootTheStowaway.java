
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author TheElk801
 */
public final class SlimefootTheStowaway extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Saproling you control");

    static {
        filter.add(new SubtypePredicate(SubType.SAPROLING));
    }

    public SlimefootTheStowaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a Saproling you control dies, Slimefoot, the Stowaway deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), false, filter);
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);

        // {4}: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new ManaCostsImpl("{4}")));
    }

    public SlimefootTheStowaway(final SlimefootTheStowaway card) {
        super(card);
    }

    @Override
    public SlimefootTheStowaway copy() {
        return new SlimefootTheStowaway(this);
    }
}
