
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public final class ScionOfVituGhazi extends CardImpl {

    public ScionOfVituGhazi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //When Scion of Vitu-Ghazi enters the battlefield, if you cast it from your hand, create a 1/1 white Bird creature token with flying, then populate.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BirdToken()), false);
        ability.addEffect(new PopulateEffect("then"));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, CastFromHandSourcePermanentCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, create a 1/1 white Bird creature token with flying, then populate."),
                new CastFromHandWatcher());
    }

    private ScionOfVituGhazi(final ScionOfVituGhazi card) {
        super(card);
    }

    @Override
    public ScionOfVituGhazi copy() {
        return new ScionOfVituGhazi(this);
    }

}
