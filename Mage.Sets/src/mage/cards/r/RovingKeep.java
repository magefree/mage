package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RovingKeep extends CardImpl {

    public RovingKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {7}: Roving Keep gets +2/+0 and gains trample until end of turn. It can attack this turn as though it didn't have defender.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn)
                        .setText("{this} gets +2/+0"), new GenericManaCost(7)
        );
        ability.addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        ability.addEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn, "it"));
        this.addAbility(ability);
    }

    private RovingKeep(final RovingKeep card) {
        super(card);
    }

    @Override
    public RovingKeep copy() {
        return new RovingKeep(this);
    }
}
// sexy hexy is back, baby!
