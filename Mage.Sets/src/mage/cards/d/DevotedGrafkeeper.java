package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevotedGrafkeeper extends CardImpl {

    public DevotedGrafkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.d.DepartedSoulkeeper.class;

        // When Devoted Grafkeeper enters the battlefield, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // Whenever you cast a spell from your graveyard, tap target creature you don't control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(), StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.NONE, Zone.GRAVEYARD
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Disturb {1}{W}{U}
        this.addAbility(new DisturbAbility(this, "{1}{W}{U}"));
    }

    private DevotedGrafkeeper(final DevotedGrafkeeper card) {
        super(card);
    }

    @Override
    public DevotedGrafkeeper copy() {
        return new DevotedGrafkeeper(this);
    }
}
