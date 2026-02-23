package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.RedMutantToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SlashReptileRampager extends CardImpl {

    public SlashReptileRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Alliance - Whenever another creature you control enters, Slash deals 2 damage to each opponent.
        this.addAbility(new AllianceAbility(new DamagePlayersEffect(2, TargetController.OPPONENT)));

        // Whenever Slash attacks, create a 2/2 red Mutant creature token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new RedMutantToken())));
    }

    private SlashReptileRampager(final SlashReptileRampager card) {
        super(card);
    }

    @Override
    public SlashReptileRampager copy() {
        return new SlashReptileRampager(this);
    }
}
