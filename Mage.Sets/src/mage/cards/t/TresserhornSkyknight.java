package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;


/**
 *
 * @author L_J
 */
public final class TresserhornSkyknight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with first strike");

    static {
        filter.add(new AbilityPredicate(FirstStrikeAbility.class));
    }

    public TresserhornSkyknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all damage that would be dealt to Tresserhorn Skyknight by creatures with first strike.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filter)));
    }

    private TresserhornSkyknight(final TresserhornSkyknight card) {
        super(card);
    }

    @Override
    public TresserhornSkyknight copy() {
        return new TresserhornSkyknight(this);
    }
}
