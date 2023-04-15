package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.Elemental11BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyrenFlamewright extends CardImpl {

    public KyrenFlamewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // {2}{R}, {T}, Discard a card: Create two 1/1 blue and red Elemental creature tokens. Creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new Elemental11BlueRedToken(), 2), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn)
                .setText("creatures you control get +1/+0"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);
    }

    private KyrenFlamewright(final KyrenFlamewright card) {
        super(card);
    }

    @Override
    public KyrenFlamewright copy() {
        return new KyrenFlamewright(this);
    }
}
