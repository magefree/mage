package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExquisiteHuntmaster extends CardImpl {

    public ExquisiteHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Exquisite Huntmaster dies, create a 1/1 green Elf Warrior creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ElfWarriorToken())));

        // Encore {4}{B}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{4}{B}")));
    }

    private ExquisiteHuntmaster(final ExquisiteHuntmaster card) {
        super(card);
    }

    @Override
    public ExquisiteHuntmaster copy() {
        return new ExquisiteHuntmaster(this);
    }
}
