package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.abilities.keyword.SneakAbility;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SharkShredderKillerClone extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("creature card from that player's graveyard");

    public SharkShredderKillerClone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Sneak {3}{B}{B}
        this.addAbility(new SneakAbility(this, "{3}{B}{B}"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Shark Shredder deals combat damage to a player,
        // put up to one target creature card from that player's graveyard onto the battlefield under your control.
        // It enters tapped and attacking that player.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new ReturnFromGraveyardToBattlefieldTargetEffect(true, true),
            false, true
        );
        ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private SharkShredderKillerClone(final SharkShredderKillerClone card) {
        super(card);
    }

    @Override
    public SharkShredderKillerClone copy() {
        return new SharkShredderKillerClone(this);
    }
}
