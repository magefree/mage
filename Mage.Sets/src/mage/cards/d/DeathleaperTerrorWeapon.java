package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathleaperTerrorWeapon extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control that entered the battlefield this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public DeathleaperTerrorWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Flesh Hooks -- Creatures you control that entered the battlefield this turn have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )).withFlavorWord("Flesh Hooks"));
    }

    private DeathleaperTerrorWeapon(final DeathleaperTerrorWeapon card) {
        super(card);
    }

    @Override
    public DeathleaperTerrorWeapon copy() {
        return new DeathleaperTerrorWeapon(this);
    }
}
