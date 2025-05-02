package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrTurnedFaceUpTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OffenderAtLarge extends CardImpl {

    public OffenderAtLarge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Disguise {4}{R}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{R}")));

        // When Offender at Large enters the battlefield or is turned face up, up to one target creature gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private OffenderAtLarge(final OffenderAtLarge card) {
        super(card);
    }

    @Override
    public OffenderAtLarge copy() {
        return new OffenderAtLarge(this);
    }
}
