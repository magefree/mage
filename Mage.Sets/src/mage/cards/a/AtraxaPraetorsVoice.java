
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class AtraxaPraetorsVoice extends CardImpl {

    public AtraxaPraetorsVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, proliferate.  (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ProliferateEffect(), TargetController.YOU, false));
    }

    private AtraxaPraetorsVoice(final AtraxaPraetorsVoice card) {
        super(card);
    }

    @Override
    public AtraxaPraetorsVoice copy() {
        return new AtraxaPraetorsVoice(this);
    }
}
