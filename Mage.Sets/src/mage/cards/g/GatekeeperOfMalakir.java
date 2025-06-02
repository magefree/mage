package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class GatekeeperOfMalakir extends CardImpl {

    public GatekeeperOfMalakir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {B} (You may pay an additional {B} as you cast this spell.)
        this.addAbility(new KickerAbility("{B}"));

        // When this creature enters, if it was kicked, target player sacrifices a creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target player")
        ).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GatekeeperOfMalakir(final GatekeeperOfMalakir card) {
        super(card);
    }

    @Override
    public GatekeeperOfMalakir copy() {
        return new GatekeeperOfMalakir(this);
    }
}
