package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.BeastToken2;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SomberwaldBeastmaster extends CardImpl {

    public SomberwaldBeastmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Somberwald Beastmaster enters the battlefield, create a 2/2 green Wolf creature token, a 3/3 green Beast creature token, and a 4/4 green Beast creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken()));
        ability.addEffect(new CreateTokenEffect(new BeastToken()).setText(", a 3/3 green Beast creature token"));
        ability.addEffect(new CreateTokenEffect(new BeastToken2()).setText(", and a 4/4 green Beast creature token"));
        this.addAbility(ability);

        // Creature tokens you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));
    }

    private SomberwaldBeastmaster(final SomberwaldBeastmaster card) {
        super(card);
    }

    @Override
    public SomberwaldBeastmaster copy() {
        return new SomberwaldBeastmaster(this);
    }
}
