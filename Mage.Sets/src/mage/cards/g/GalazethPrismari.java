package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalazethPrismari extends CardImpl {

    public GalazethPrismari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Galazeth Prismari enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Artifacts you control have "{T}: Add one mana of any color. Spend this mana only to cast an instant or sorcery spell."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ConditionalAnyColorManaAbility(1, new InstantOrSorcerySpellManaBuilder()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ARTIFACTS
        )));
    }

    private GalazethPrismari(final GalazethPrismari card) {
        super(card);
    }

    @Override
    public GalazethPrismari copy() {
        return new GalazethPrismari(this);
    }
}
