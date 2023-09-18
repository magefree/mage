package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SerfToken;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class SengirAutocrat extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Serf tokens");

    static {
        filter.add(SubType.SERF.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public SengirAutocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sengir Autocrat enters the battlefield, create three 0/1 black Serf creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SerfToken(), 3)));

        // When Sengir Autocrat leaves the battlefield, exile all Serf tokens.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filter), false));
    }

    private SengirAutocrat(final SengirAutocrat card) {
        super(card);
    }

    @Override
    public SengirAutocrat copy() {
        return new SengirAutocrat(this);
    }
}
