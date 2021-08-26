package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public class IcingdeathFrostTongueToken extends TokenImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public IcingdeathFrostTongueToken() {
        super("Icingdeath, Frost Tongue", "Icingdeath, Frost Tongue, a legendary white " +
                "Equipment artifact token with \"Equipped creature gets +2/+0,\" " +
                "\"Whenever equipped creature attacks, tap target creature " +
                "defending player controls,\" and equip {2}");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        color.setWhite(true);
        subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Whenever equipped creature attacks, tap target creature defending player controls
        Ability ability = new AttacksAttachedTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));

        availableImageSetCodes = Arrays.asList("AFR");
    }

    private IcingdeathFrostTongueToken(final IcingdeathFrostTongueToken token) {
        super(token);
    }

    @Override
    public IcingdeathFrostTongueToken copy() {
        return new IcingdeathFrostTongueToken(this);
    }
}
