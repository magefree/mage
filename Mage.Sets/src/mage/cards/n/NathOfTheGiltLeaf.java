package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.ElfWarriorToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NathOfTheGiltLeaf extends CardImpl {

    public NathOfTheGiltLeaf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may have target opponent discard a card at random.
        Effect effect = new DiscardTargetEffect(1, true);
        effect.setText("you may have target opponent discard a card at random");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever an opponent discards a card, you may create a 1/1 green Elf Warrior creature token.
        Effect effect2 = new CreateTokenEffect(new ElfWarriorToken());
        effect2.setText("you may create a 1/1 green Elf Warrior creature token");
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(effect2, true));

    }

    private NathOfTheGiltLeaf(final NathOfTheGiltLeaf card) {
        super(card);
    }

    @Override
    public NathOfTheGiltLeaf copy() {
        return new NathOfTheGiltLeaf(this);
    }
}
