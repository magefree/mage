package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanguardSuppressor extends CardImpl {

    public VanguardSuppressor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Squad {2}
        this.addAbility(new SquadAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Suppressing Fire -- Whenever Vanguard Suppressor deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ).withFlavorWord("Suppressing Fire"));
    }

    private VanguardSuppressor(final VanguardSuppressor card) {
        super(card);
    }

    @Override
    public VanguardSuppressor copy() {
        return new VanguardSuppressor(this);
    }
}
