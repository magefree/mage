package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EmmaraTandris extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public EmmaraTandris(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Prevent all damage that would be dealt to creature tokens you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS)));
    }

    private EmmaraTandris(final EmmaraTandris card) {
        super(card);
    }

    @Override
    public EmmaraTandris copy() {
        return new EmmaraTandris(this);
    }
}
