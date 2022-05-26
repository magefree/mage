package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LurkingGreenDragon extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public LurkingGreenDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lurking Green Dragon can't attack defending player controls a creature with flying.
        this.addAbility(new SimpleStaticAbility(new CantAttackUnlessDefenderControllsPermanent(filter)));
    }

    private LurkingGreenDragon(final LurkingGreenDragon card) {
        super(card);
    }

    @Override
    public LurkingGreenDragon copy() {
        return new LurkingGreenDragon(this);
    }
}
