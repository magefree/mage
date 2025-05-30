package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HeroToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionsFromBeyond extends CardImpl {

    public ChampionsFromBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{W}{W}");

        // When this enchantment enters, create X 1/1 colorless Hero creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HeroToken(), GetXValue.instance)));

        // Light Party -- Whenever you attack with four or more creatures, scry 2, then draw a card.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new ScryEffect(2, false), 4);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability.withFlavorWord("Light Party"));

        // Full Party -- Whenever you attack with eight or more creatures, those creatures get +4/+4 until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(4, 4)
                        .setText("those creatures get +4/+4 until end of turn"),
                8, StaticFilters.FILTER_PERMANENT_CREATURES, true
        ).withFlavorWord("Full Party"));
    }

    private ChampionsFromBeyond(final ChampionsFromBeyond card) {
        super(card);
    }

    @Override
    public ChampionsFromBeyond copy() {
        return new ChampionsFromBeyond(this);
    }
}
