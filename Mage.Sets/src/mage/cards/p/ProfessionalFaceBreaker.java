package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessionalFaceBreaker extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.TREASURE, "a Treasure");

    public ProfessionalFaceBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever one or more creatures you control deal combat damage to a player, create a Treasure token.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Sacrifice a Treasure: Exile the top card of your library. You may play that card this turn.
        this.addAbility(new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1, false), new SacrificeTargetCost(filter)
        ));
    }

    private ProfessionalFaceBreaker(final ProfessionalFaceBreaker card) {
        super(card);
    }

    @Override
    public ProfessionalFaceBreaker copy() {
        return new ProfessionalFaceBreaker(this);
    }
}
