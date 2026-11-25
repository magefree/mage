package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MurfToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class RokTahkBuddingScientist extends CardImpl {

    public RokTahkBuddingScientist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BRIKAR);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Creatures you control have "{T}: Add {G}{G}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES
        )));

        // When Rok-Tahk enters, create Murf, a legendary 0/1 green Ooze Slug creature token with indestructible.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MurfToken())));
    }

    private RokTahkBuddingScientist(final RokTahkBuddingScientist card) {
        super(card);
    }

    @Override
    public RokTahkBuddingScientist copy() {
        return new RokTahkBuddingScientist(this);
    }
}
