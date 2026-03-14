
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author anonymous
 */
public final class SvogthosTheRestlessTomb extends CardImpl {

    private static final CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);

    public SvogthosTheRestlessTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}{B}{G}: Until end of turn, Svogthos, the Restless Tomb becomes a black and green Plant Zombie creature with "This creature's power and toughness are each equal to the number of creature cards in your graveyard." It's still a land.
        Ability ability = new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    0, 0,
                    "black and green Plant Zombie creature with \"This creature's power and toughness are each equal to the number of creature cards in your graveyard.\"",
                    SubType.PLANT, SubType.ZOMBIE
                ).withColor("GB")
                .withAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(count, Duration.WhileOnBattlefield))),
                CardType.LAND,
                Duration.EndOfTurn
            ).withDurationRuleAtStart(true),
            new ManaCostsImpl<>("{3}{B}{G}")
        );
        this.addAbility(ability);
    }

    private SvogthosTheRestlessTomb(final SvogthosTheRestlessTomb card) {
        super(card);
    }

    @Override
    public SvogthosTheRestlessTomb copy() {
        return new SvogthosTheRestlessTomb(this);
    }
}
