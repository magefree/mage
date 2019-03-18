package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpearSpewer extends CardImpl {

    public SpearSpewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Spear Spewer deals 1 damage to each player.
        this.addAbility(new SimpleActivatedAbility(new DamagePlayersEffect(1), new TapSourceCost()));
    }

    private SpearSpewer(final SpearSpewer card) {
        super(card);
    }

    @Override
    public SpearSpewer copy() {
        return new SpearSpewer(this);
    }
}
