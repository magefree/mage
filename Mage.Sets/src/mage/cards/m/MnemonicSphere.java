package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MnemonicSphere extends CardImpl {

    public MnemonicSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        // {1}{U}, Sacrifice Mnemonic Sphere: Draw two cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Channel — {U}, Discard Mnemonic Sphere: Draw a card.
        this.addAbility(new ChannelAbility("{U}", new DrawCardSourceControllerEffect(1)));
    }

    private MnemonicSphere(final MnemonicSphere card) {
        super(card);
    }

    @Override
    public MnemonicSphere copy() {
        return new MnemonicSphere(this);
    }
}
