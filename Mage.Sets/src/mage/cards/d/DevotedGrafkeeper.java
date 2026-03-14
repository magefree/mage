package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class DevotedGrafkeeper extends TransformingDoubleFacedCard {

    public DevotedGrafkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{W}{U}",
                "Departed Soulkeeper",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "WU");

        // Devoted Grafkeeper
        this.getLeftHalfCard().setPT(2, 1);

        // When Devoted Grafkeeper enters the battlefield, mill two cards.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // Whenever you cast a spell from your graveyard, tap target creature you don't control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(), StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.NONE, Zone.GRAVEYARD
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getLeftHalfCard().addAbility(ability);

        // Disturb {1}{W}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{1}{W}{U}"));

        // Departed Soulkeeper
        this.getRightHalfCard().setPT(3, 1);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Departed Soulkeeper can block only creatures with flying.
        this.getRightHalfCard().addAbility(new CanBlockOnlyFlyingAbility());

        // If Departed Soulkeeper would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private DevotedGrafkeeper(final DevotedGrafkeeper card) {
        super(card);
    }

    @Override
    public DevotedGrafkeeper copy() {
        return new DevotedGrafkeeper(this);
    }
}
