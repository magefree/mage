
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SerfToken;

/**
 *
 * @author Quercitron
 */
public final class SengirAutocrat extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Serf tokens");

    static {
        filter.add(new SubtypePredicate(SubType.SERF));
        filter.add(new TokenPredicate());
    }

    public SengirAutocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sengir Autocrat enters the battlefield, create three 0/1 black Serf creature tokens.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SerfToken(), 3));
        this.addAbility(ability);
        // When Sengir Autocrat leaves the battlefield, exile all Serf tokens.
        ability = new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filter), false);
        this.addAbility(ability);
    }

    public SengirAutocrat(final SengirAutocrat card) {
        super(card);
    }

    @Override
    public SengirAutocrat copy() {
        return new SengirAutocrat(this);
    }
}
