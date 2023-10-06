package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShieldMare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ShieldMare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Shield Mare can't be blocked by red creatures.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));

        // When Shield Mare enters the battlefield or becomes the target of a spell or ability and opponent controls, you gain 3 life.
        this.addAbility(new OrTriggeredAbility(Zone.ALL, new GainLifeEffect(3), false,
                "When {this} enters the battlefield or becomes the target of a spell or ability an opponent controls, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesTargetSourceTriggeredAbility(null, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS)));
    }

    private ShieldMare(final ShieldMare card) {
        super(card);
    }

    @Override
    public ShieldMare copy() {
        return new ShieldMare(this);
    }
}
