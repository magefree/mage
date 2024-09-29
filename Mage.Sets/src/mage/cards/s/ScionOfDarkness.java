package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ScionOfDarkness extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card from that player's graveyard");

    public ScionOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Scion of Darkness deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true, true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);

        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));
    }

    private ScionOfDarkness(final ScionOfDarkness card) {
        super(card);
    }

    @Override
    public ScionOfDarkness copy() {
        return new ScionOfDarkness(this);
    }
}
