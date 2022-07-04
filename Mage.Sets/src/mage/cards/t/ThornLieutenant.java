package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornLieutenant extends CardImpl {

    public ThornLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Thorn Lieutenant becomes the target of a spell or ability an opponent controls, create a 1/1 green Elf Warrior creature token.
        this.addAbility(new BecomesTargetTriggeredAbility(
                new CreateTokenEffect(new ElfWarriorToken()),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS
        ));

        // {5}{G}: Thorn Lieutenant gets +4/+4 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(4, 4, Duration.EndOfTurn),
                new ManaCostsImpl<>("{5}{G}")
        ));
    }

    private ThornLieutenant(final ThornLieutenant card) {
        super(card);
    }

    @Override
    public ThornLieutenant copy() {
        return new ThornLieutenant(this);
    }
}
