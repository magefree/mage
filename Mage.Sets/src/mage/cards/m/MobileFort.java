package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MobileFort extends CardImpl {

    public MobileFort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // {3}: Mobile Fort gets +3/-1 until end of turn and can attack this turn as though it didn't have defender. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, -1, Duration.EndOfTurn), new GenericManaCost(3));
        ability.addEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn, "and"));
        this.addAbility(ability);
    }

    private MobileFort(final MobileFort card) {
        super(card);
    }

    @Override
    public MobileFort copy() {
        return new MobileFort(this);
    }
}
