package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ServantOfTymaret extends CardImpl {

    public ServantOfTymaret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // <i>Inspired</i> &mdash; Whenever Servant of Tymaret becomes untapped, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new InspiredAbility(new LoseLifeOpponentsYouGainLifeLostEffect(1)));

        // {2}{B}: Regenerate Servant of Tymaret.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private ServantOfTymaret(final ServantOfTymaret card) {
        super(card);
    }

    @Override
    public ServantOfTymaret copy() {
        return new ServantOfTymaret(this);
    }
}
