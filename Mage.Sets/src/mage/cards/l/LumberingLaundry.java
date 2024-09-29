package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.LookAtOpponentFaceDownCreaturesAnyTimeEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class LumberingLaundry extends CardImpl {

    public LumberingLaundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {2}: Until end of turn, you may look at face-down creatures you don't control any time.
        this.addAbility(new SimpleActivatedAbility(new LookAtOpponentFaceDownCreaturesAnyTimeEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));

        // Disguise {5}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{5}")));

    }

    private LumberingLaundry(final LumberingLaundry card) {
        super(card);
    }

    @Override
    public LumberingLaundry copy() {
        return new LumberingLaundry(this);
    }
}
