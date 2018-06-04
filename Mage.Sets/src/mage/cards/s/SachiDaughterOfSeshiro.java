
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author Loki
 */
public final class SachiDaughterOfSeshiro extends CardImpl {

    private static final FilterCreaturePermanent snakeFilter = new FilterCreaturePermanent("Snakes");
    private static final FilterPermanent shamanFilter = new FilterPermanent("Shamans");

    static {
        snakeFilter.add(new SubtypePredicate(SubType.SNAKE));
        shamanFilter.add(new SubtypePredicate(SubType.SHAMAN));
    }

    public SachiDaughterOfSeshiro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Other Snake creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, snakeFilter, true)));
        // Shamans you control have "{T}: Add {G}{G}."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()), Duration.WhileOnBattlefield, shamanFilter, false)));
    }

    public SachiDaughterOfSeshiro(final SachiDaughterOfSeshiro card) {
        super(card);
    }

    @Override
    public SachiDaughterOfSeshiro copy() {
        return new SachiDaughterOfSeshiro(this);
    }

}
