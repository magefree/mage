
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;

/**
 *
 * @author emerald000
 */
public final class BattlewandOak extends CardImpl {
    
    private static final FilterPermanent filterForest = new FilterPermanent("a Forest");
    private static final FilterSpell filterTreefolk = new FilterSpell("a Treefolk spell");
    static {
        filterForest.add(SubType.FOREST.getPredicate());
        filterTreefolk.add(SubType.TREEFOLK.getPredicate());
    }

    public BattlewandOak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.TREEFOLK, SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a Forest enters the battlefield under your control, Battlewand Oak gets +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), filterForest));
        
        // Whenever you cast a Treefolk spell, Battlewand Oak gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), filterTreefolk, false));
    }

    private BattlewandOak(final BattlewandOak card) {
        super(card);
    }

    @Override
    public BattlewandOak copy() {
        return new BattlewandOak(this);
    }
}
