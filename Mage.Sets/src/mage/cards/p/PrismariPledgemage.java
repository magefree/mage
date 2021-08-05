package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismariPledgemage extends CardImpl {

    public PrismariPledgemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}{U/R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Prismari Pledgemage can attack this turn as though it didn't have defender.
        this.addAbility(new MagecraftAbility(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn)));
    }

    private PrismariPledgemage(final PrismariPledgemage card) {
        super(card);
    }

    @Override
    public PrismariPledgemage copy() {
        return new PrismariPledgemage(this);
    }
}
