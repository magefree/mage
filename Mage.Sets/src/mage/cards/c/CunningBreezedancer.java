package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class CunningBreezedancer extends CardImpl {

    public CunningBreezedancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a noncreature spell, Cunning Breezedancer gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));
    }

    private CunningBreezedancer(final CunningBreezedancer card) {
        super(card);
    }

    @Override
    public CunningBreezedancer copy() {
        return new CunningBreezedancer(this);
    }
}
