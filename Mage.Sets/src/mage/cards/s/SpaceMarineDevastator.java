package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpaceMarineDevastator extends CardImpl {

    public SpaceMarineDevastator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Squad {2}
        this.addAbility(new SquadAbility());

        // Grav-cannon -- When Space Marine Devastator enters the battlefield, destroy up to one target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT
        ));
        this.addAbility(ability.withFlavorWord("Grav-cannon"));
    }

    private SpaceMarineDevastator(final SpaceMarineDevastator card) {
        super(card);
    }

    @Override
    public SpaceMarineDevastator copy() {
        return new SpaceMarineDevastator(this);
    }
}
