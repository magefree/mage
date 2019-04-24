
package mage.cards.o;

import java.util.UUID;
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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.RatToken;

/**
 *
 * @author LevelX2
 */
public final class OgreSlumlord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Rats you control");
    static {
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new AnotherPredicate());
        filter2.add(new SubtypePredicate(SubType.RAT));
    }
    
    
    public OgreSlumlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);


        // Whenever another nontoken creature dies, you may create a 1/1 black Rat creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new RatToken()), true, filter));
        // Rats you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter2)));
         
    }

    public OgreSlumlord(final OgreSlumlord card) {
        super(card);
    }

    @Override
    public OgreSlumlord copy() {
        return new OgreSlumlord(this);
    }
}
