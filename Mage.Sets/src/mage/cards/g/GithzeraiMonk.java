package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GithzeraiMonk extends CardImpl {

    public GithzeraiMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Psychic Defense — When Githzerai Monk enters the battlefield, tap all creatures you don't control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new TapAllEffect(StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL)
        ).withFlavorWord("Psychic Defense"));
    }

    private GithzeraiMonk(final GithzeraiMonk card) {
        super(card);
    }

    @Override
    public GithzeraiMonk copy() {
        return new GithzeraiMonk(this);
    }
}
