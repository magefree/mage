
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author Plopman
 */
public final class WirewoodHivemaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken Elf");
    static {
        filter.add(new SubtypePredicate(SubType.ELF));
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(TokenPredicate.instance));
    }
    public WirewoodHivemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another nontoken Elf enters the battlefield, you may create a 1/1 green Insect creature token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new InsectToken(), 1), filter, true));
    }

    public WirewoodHivemaster(final WirewoodHivemaster card) {
        super(card);
    }

    @Override
    public WirewoodHivemaster copy() {
        return new WirewoodHivemaster(this);
    }
}
