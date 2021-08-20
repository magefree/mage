package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.RatToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OgreSlumlord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Rats you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        filter2.add(SubType.RAT.getPredicate());
    }


    public OgreSlumlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another nontoken creature dies, you may create a 1/1 black Rat creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new RatToken()), true, filter));

        // Rats you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter2)));

    }

    private OgreSlumlord(final OgreSlumlord card) {
        super(card);
    }

    @Override
    public OgreSlumlord copy() {
        return new OgreSlumlord(this);
    }
}
