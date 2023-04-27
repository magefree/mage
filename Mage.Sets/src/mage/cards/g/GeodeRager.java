package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeodeRager extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public GeodeRager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, goad each creature target player controls.
        Ability ability = new LandfallAbility(new GoadAllEffect(filter).setText("goad each creature target player controls"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GeodeRager(final GeodeRager card) {
        super(card);
    }

    @Override
    public GeodeRager copy() {
        return new GeodeRager(this);
    }
}
