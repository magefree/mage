package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
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
public final class StalkedResearcher extends CardImpl {

    public StalkedResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, Stalked Researcher can attack this turn as though it didn't have defender.
        this.addAbility(new EerieAbility(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn)));
    }

    private StalkedResearcher(final StalkedResearcher card) {
        super(card);
    }

    @Override
    public StalkedResearcher copy() {
        return new StalkedResearcher(this);
    }
}
