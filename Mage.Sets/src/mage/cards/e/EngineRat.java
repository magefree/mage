package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EngineRat extends CardImpl {

    public EngineRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {5}{B}: Each opponent loses 2 life.
        this.addAbility(new SimpleActivatedAbility(new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{5}{B}")));
    }

    private EngineRat(final EngineRat card) {
        super(card);
    }

    @Override
    public EngineRat copy() {
        return new EngineRat(this);
    }
}
