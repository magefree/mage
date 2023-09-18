
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author fireshoes
 */
public final class DrogskolCavalry extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Spirit");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public DrogskolCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another Spirit enters the battlefield under your control, you gain 2 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(2), filter));

        // {3}{W}: Create a 1/1 white Spirit creature token with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()), new ManaCostsImpl<>("{3}{W}")));
    }

    private DrogskolCavalry(final DrogskolCavalry card) {
        super(card);
    }

    @Override
    public DrogskolCavalry copy() {
        return new DrogskolCavalry(this);
    }
}
