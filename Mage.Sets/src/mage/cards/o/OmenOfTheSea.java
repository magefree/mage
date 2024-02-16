package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenOfTheSea extends CardImpl {

    public OmenOfTheSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Omen of the Sea enters the battlefield, scry 2, then draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(2, false));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // {2}{U}, Sacrifice Omen of the Sea: Scry 2.
        ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OmenOfTheSea(final OmenOfTheSea card) {
        super(card);
    }

    @Override
    public OmenOfTheSea copy() {
        return new OmenOfTheSea(this);
    }
}
