package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningWolf extends CardImpl {

    public LightningWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {1}{R}: Lightning Wolf gains first strike until end of turn. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}")));
    }

    private LightningWolf(final LightningWolf card) {
        super(card);
    }

    @Override
    public LightningWolf copy() {
        return new LightningWolf(this);
    }
}
