package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.OfficerToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ShuttlecraftGalileo extends CardImpl {

    public ShuttlecraftGalileo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Landfall -- Whenever a land you control enters, create a 1/1 red Officer creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new OfficerToken())));

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying
        // Whenever Shuttlecraft Galileo attacks, up to seven target attacking creatures gain flying until end of turn.
        // 3/3
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        ability.addTarget(new TargetPermanent(0, 7, StaticFilters.FILTER_ATTACKING_CREATURES));
        this.addAbility(new StationLevelAbility(6)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(ability)
            .withPT(3, 3));
    }

    private ShuttlecraftGalileo(final ShuttlecraftGalileo card) {
        super(card);
    }

    @Override
    public ShuttlecraftGalileo copy() {
        return new ShuttlecraftGalileo(this);
    }
}
