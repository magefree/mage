package mage.cards.r;

import mage.MageInt;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class RuthlessRadrat extends CardImpl {

    public RuthlessRadrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squad -- Exile four cards from your graveyard.
        this.addAbility(new SquadAbility(new ExileFromGraveCost(new TargetCardInYourGraveyard(4))));

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private RuthlessRadrat(final RuthlessRadrat card) {
        super(card);
    }

    @Override
    public RuthlessRadrat copy() {
        return new RuthlessRadrat(this);
    }
}
