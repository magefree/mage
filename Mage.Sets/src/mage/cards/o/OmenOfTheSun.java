package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenOfTheSun extends CardImpl {

    public OmenOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Omen of the Sun enters the battlefield, create two 1/1 white Human Soldier creature tokens and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierToken(), 2)
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

        // {2}{W}, Sacrifice Omen of the Sun: Scry 2.
        ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OmenOfTheSun(final OmenOfTheSun card) {
        super(card);
    }

    @Override
    public OmenOfTheSun copy() {
        return new OmenOfTheSun(this);
    }
}
