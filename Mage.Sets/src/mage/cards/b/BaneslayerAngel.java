

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BaneslayerAngel extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Demons and from Dragons");

    static {
        filter.add(Predicates.or(SubType.DEMON.getPredicate(), SubType.DRAGON.getPredicate()));
    }

    public BaneslayerAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying, first strike, lifelink, protection from Demons and from Dragons
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
    }

    private BaneslayerAngel(final BaneslayerAngel card) {
        super(card);
    }

    @Override
    public BaneslayerAngel copy() {
        return new BaneslayerAngel(this);
    }

}
