package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class KrotiqNestguard extends CardImpl {

    public KrotiqNestguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}{G}: This creature can attack this turn as though it didn't have defender.
        this.addAbility(new SimpleActivatedAbility(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private KrotiqNestguard(final KrotiqNestguard card) {
        super(card);
    }

    @Override
    public KrotiqNestguard copy() {
        return new KrotiqNestguard(this);
    }
}
