
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class BastionMastodon extends CardImpl {

    public BastionMastodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {W}: Bastion Mastodon gets vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        this.addAbility(ability);
    }

    private BastionMastodon(final BastionMastodon card) {
        super(card);
    }

    @Override
    public BastionMastodon copy() {
        return new BastionMastodon(this);
    }
}
