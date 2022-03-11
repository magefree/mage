package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class WrensRunPackmaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WOLF, "Wolves");

    public WrensRunPackmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Champion an Elf
        this.addAbility(new ChampionAbility(this, SubType.ELF));

        // {2}{G}: Create a 2/2 green Wolf creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new WolfToken()), new ManaCostsImpl<>("{2}{G}")
        ));

        // Each Wolf you control has deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private WrensRunPackmaster(final WrensRunPackmaster card) {
        super(card);
    }

    @Override
    public WrensRunPackmaster copy() {
        return new WrensRunPackmaster(this);
    }
}
