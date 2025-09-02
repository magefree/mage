package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.WebSlingingCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.Spider21Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpidersManHeroicHorde extends CardImpl {

    public SpidersManHeroicHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Web-slinging {4}{G}{G}
        this.addAbility(new WebSlingingAbility(this, "{4}{G}{G}"));

        // When Spiders-Man enters, if they were cast using web-slinging, you gain 3 life and create two 2/1 green Spider creature tokens with reach.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)).withInterveningIf(WebSlingingCondition.THEY);
        ability.addEffect(new CreateTokenEffect(new Spider21Token(), 2).concatBy("and"));
        this.addAbility(ability);
    }

    private SpidersManHeroicHorde(final SpidersManHeroicHorde card) {
        super(card);
    }

    @Override
    public SpidersManHeroicHorde copy() {
        return new SpidersManHeroicHorde(this);
    }
}
